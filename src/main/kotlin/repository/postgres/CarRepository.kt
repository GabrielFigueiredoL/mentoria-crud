package org.gabrielfigueiredol.repository.postgres

import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.repository.interfaces.ICarRepository
import org.gabrielfigueiredol.utils.ResultSetToEntity
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CarRepository : ICarRepository {
    override fun saveCar(car: Car): Int {
        val query = """
            INSERT INTO cars
            (model, model_year, manufacture_year, km, price_in_cents, color, chassis, brand, transmission_type)
            VALUES 
            (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { preparedStatement ->
                preparedStatement.setString(1, car.model)
                preparedStatement.setInt(2, car.modelYear)
                preparedStatement.setInt(3, car.manufactureYear)
                preparedStatement.setInt(4, car.km)
                preparedStatement.setInt(5, car.priceInCents)
                preparedStatement.setString(6, car.color)
                preparedStatement.setString(7, car.chassis)
                preparedStatement.setString(8, car.brand.name)
                preparedStatement.setString(9, car.transmissionType.name)

                preparedStatement.executeUpdate()
                preparedStatement.generatedKeys.use { resultSet ->
                    if (resultSet.next()) {
                        val id = resultSet.getInt(1)
                        return id
                    } else {
                        throw SQLException("Erro SQL")
                    }
                }
            }
        }
    }

    override fun getCarById(id: Int): Car? {
        val query = """
            SELECT * FROM cars WHERE id = ?
        """.trimIndent()
        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return ResultSetToEntity.toCar(resultSet)
                    }
                }
            }
        }
        return null
    }

    override fun getAllCars(): List<Car> {
        val carsList = mutableListOf<Car>()

        PostgresDB.getConnection().use { connection ->
            connection.createStatement().executeQuery("SELECT * FROM cars").use { resultSet: ResultSet ->
                    while (resultSet.next()) {
                        val car = ResultSetToEntity.toCar(resultSet)
                        carsList.add(car)
                    }
            }
        }

        return carsList
    }

    override fun deleteCarById(id: Int): Boolean {
        val query = """
        DELETE FROM cars WHERE id = ?
    """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val rowsAffected = preparedStatement.executeUpdate()
                return rowsAffected > 0
            }
        }
    }

    override fun updateCar(id: Int, updatedCar: Car): Boolean {
        val query = """
            UPDATE cars
            SET model = ?,
            model_year = ?,
            manufacture_year = ?,
            km = ?, 
            price_in_cents = ?, 
            color = ?, 
            chassis = ?, 
            brand = ?, 
            transmission_type = ?
            WHERE id = ?
        """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { preparedStatement ->
                preparedStatement.setString(1, updatedCar.model)
                preparedStatement.setInt(2, updatedCar.modelYear)
                preparedStatement.setInt(3, updatedCar.manufactureYear)
                preparedStatement.setInt(4, updatedCar.km)
                preparedStatement.setInt(5, updatedCar.priceInCents)
                preparedStatement.setString(6, updatedCar.color)
                preparedStatement.setString(7, updatedCar.chassis)
                preparedStatement.setString(8, updatedCar.brand.name)
                preparedStatement.setString(9, updatedCar.transmissionType.name)
                preparedStatement.setInt(10, id)

                return preparedStatement.executeUpdate() > 0
            }
        }
    }
}