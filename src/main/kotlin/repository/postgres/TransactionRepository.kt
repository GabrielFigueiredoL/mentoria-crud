package org.gabrielfigueiredol.repository.postgres

import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.domain.Transaction
import org.gabrielfigueiredol.repository.interfaces.ITransactionRepository
import org.gabrielfigueiredol.utils.ResultSetToEntity
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import kotlin.use

class TransactionRepository : ITransactionRepository {
    override fun saveTransaction(transaction: Transaction): Int {
        val query = """
            INSERT INTO transactions
            (transaction_type, car_id, client_id, price_in_cents)
            VALUES 
            (?, ?, ?, ?)
            RETURNING id
        """.trimIndent()

        println("postgres")
        try {
            PostgresDB.getConnection().use { connection ->
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { preparedStatement ->
                    preparedStatement.setString(1, transaction.transactionType.name)
                    preparedStatement.setInt(2, transaction.carId)
                    preparedStatement.setInt(3, transaction.clientId)
                    preparedStatement.setInt(4, transaction.priceInCents)
                    preparedStatement.executeUpdate()

                    preparedStatement.generatedKeys.use { resultSet ->
                        if (resultSet.next()) {
                            println("id - resultset")
                            val id = resultSet.getInt(1)
                            return id
                        } else {
                            println("erro")
                            throw SQLException("Erro SQL")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 1
    }

    override fun getTransactionById(id: Int): Transaction? {
        val query = """
            SELECT * FROM transactions WHERE id = ?
        """.trimIndent()
        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return ResultSetToEntity.toTransaction(resultSet)
                    }
                }
            }
        }
        return null
    }

    override fun getAllTransactions(): List<Transaction> {
        val transactionsList = mutableListOf<Transaction>()

        PostgresDB.getConnection().use { connection ->
            connection.createStatement().executeQuery("SELECT * FROM transactions").use { resultSet: ResultSet ->
                while (resultSet.next()) {
                    val transaction = ResultSetToEntity.toTransaction(resultSet)
                    transactionsList.add(transaction)
                }
            }
        }

        return transactionsList
    }

    override fun deleteTransactionById(id: Int): Boolean {
        val query = """
        DELETE FROM transactions WHERE id = ?
    """.trimIndent()

        PostgresDB.getConnection().use { connection ->
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val rowsAffected = preparedStatement.executeUpdate()
                return rowsAffected > 0
            }
        }
    }
}