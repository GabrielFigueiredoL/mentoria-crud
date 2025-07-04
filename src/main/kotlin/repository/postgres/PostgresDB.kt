package org.gabrielfigueiredol.repository.postgres

import java.nio.file.Files
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object PostgresDB {
    private const val URL = "jdbc:postgresql://localhost:5432/mentoria"
    private const val USER = "docker"
    private const val PASSWORD = "docker"
    private val carSQL = object {}.javaClass.getResource("/db/postgres/create-table-cars.sql").readText()
    private val clientSQL = object {}.javaClass.getResource("/db/postgres/create-table-clients.sql").readText()
    private val transactionsSQL = object {}.javaClass.getResource("/db/postgres/create-table-transactions.sql").readText()


    init {
        try {
            DriverManager.getConnection(URL, USER, PASSWORD).use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(carSQL)
                    statement.execute(clientSQL)
                    statement.execute(transactionsSQL)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}