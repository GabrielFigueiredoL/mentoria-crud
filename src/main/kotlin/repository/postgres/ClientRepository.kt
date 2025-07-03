package org.gabrielfigueiredol.repository.postgres

import org.gabrielfigueiredol.domain.Client
import org.gabrielfigueiredol.repository.interfaces.IClientRepository
import org.gabrielfigueiredol.utils.ResultSetToEntity
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ClientRepository : IClientRepository {
    override fun saveClient(client: Client): Int {
        val query = """
            INSERT INTO clients
            (name, document, phone, street, complement, neighborhood, city, cep)
            VALUES
            (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { preparedStatement ->
                preparedStatement.setString(1, client.name)
                preparedStatement.setString(2, client.document)
                preparedStatement.setString(3, client.phone)
                preparedStatement.setString(4, client.street)
                preparedStatement.setString(5, client.complement)
                preparedStatement.setString(6, client.neighborhood)
                preparedStatement.setString(7, client.city)
                preparedStatement.setString(8, client.cep)

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

    override fun getClientById(id: Int): Client? {
        val query = """
            SELECT * FROM clients WHERE id = ?
        """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return ResultSetToEntity.toClient(resultSet)
                    }
                }
            }
        }

        return null
    }

    override fun getAllClients(): List<Client> {
        val clientList = mutableListOf<Client>()

        PostgresDB.getConnection().use { connection ->
            connection.createStatement().executeQuery("SELECT * FROM clients").use { resultSet: ResultSet ->
                while (resultSet.next()) {
                    val client = ResultSetToEntity.toClient(resultSet)
                    clientList.add(client)
                }
            }
        }

        return clientList
    }

    override fun deleteClientById(id: Int): Boolean {
        val query = """
        DELETE FROM clients WHERE id = ?
    """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val rowsAffected = preparedStatement.executeUpdate()
                return rowsAffected > 0
            }
        }
    }

    override fun updateClient(id: Int, updatedClient: Client): Boolean {
        val query = """
            UPDATE clients
            SET name = ?,
            document = ?,
            phone = ?,
            street = ?, 
            complement = ?, 
            neighborhood = ?, 
            city = ?, 
            cep = ?
            WHERE id = ? 
        """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setString(1, updatedClient.name)
                preparedStatement.setString(2, updatedClient.document)
                preparedStatement.setString(3, updatedClient.phone)
                preparedStatement.setString(4, updatedClient.street)
                preparedStatement.setString(5, updatedClient.complement)
                preparedStatement.setString(6, updatedClient.neighborhood)
                preparedStatement.setString(7, updatedClient.city)
                preparedStatement.setString(8, updatedClient.cep)
                preparedStatement.setInt(9, id)

                return preparedStatement.executeUpdate() > 0
            }
        }
    }
}