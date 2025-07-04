package org.gabrielfigueiredol.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import kotlinx.serialization.json.Json
import org.gabrielfigueiredol.domain.Transaction
import org.gabrielfigueiredol.service.TransactionService

class TransactionApi(private val server: HttpServer) {
    private val path = "/transactions"
    val transactionService = TransactionService()

    fun initTransactionApi() {
        server.createContext(path) { exchange ->
            when (exchange?.requestMethod) {
                "POST" -> register(exchange)
                "GET" -> getAll(exchange)
            }
        }
        server.createContext("$path/") {exchange ->
            val id = exchange.requestURI.path.removePrefix("$path/").toIntOrNull()
            if (id == null) {
                exchange.sendResponseHeaders(400, -1)
                exchange.close()
            } else {
                when (exchange?.requestMethod) {
                    "GET" -> getById(exchange, id)
                    "DELETE" -> deleteById(exchange, id)
                }
            }
        }
    }

    fun register(exchange: HttpExchange) {
        println("json")
        val json = exchange.requestBody.bufferedReader().readText()
        println("transaction")
        val transaction = Json.decodeFromString<Transaction>(json)
        println("id")
        val transactionId = transactionService.registerTransaction(transaction)
        val response = """{"id": $transactionId}"""
        exchange.sendResponseHeaders(201, response.toByteArray().size.toLong())
        exchange.responseBody.write(response.toByteArray())
        exchange.close()
    }

    fun getAll(exchange: HttpExchange) {
        val transactionList = transactionService.getAllTransactions()
        val response = Json.encodeToString(transactionList)
        exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
        exchange.responseBody.write(response.toByteArray())
        exchange.close()
    }

    fun getById(exchange: HttpExchange, id: Int) {
        val transaction: Transaction? = transactionService.getTransactionById(id)
        if (transaction != null) {
            val response = Json.encodeToString(transaction)
            exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
            exchange.responseBody.write(response.toByteArray())
        } else {
            exchange.sendResponseHeaders(404, -1)
        }
        exchange.close()
    }

    fun deleteById(exchange: HttpExchange, id: Int) {
        val success = transactionService.deleteTransactionById(id)
        if (success) {
            exchange.sendResponseHeaders(204, -1)
        } else {
            exchange.sendResponseHeaders(404, -1)
        }
        exchange.close()
    }
}