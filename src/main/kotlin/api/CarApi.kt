package org.gabrielfigueiredol.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import kotlinx.serialization.json.Json
import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.service.CarService

class CarApi (private val server: HttpServer){
    private val path = "/cars"
    val carService = CarService()

    fun initCarApi() {
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
                    "PUT" -> update(exchange, id)
                }
            }
        }
    }

    fun register(exchange: HttpExchange) {
        val json = exchange.requestBody.bufferedReader().readText()
        val car = Json.decodeFromString<Car>(json)
        val carId = carService.registerCar(car)
        val response = """{"id": $carId}"""
        exchange.sendResponseHeaders(201, response.toByteArray().size.toLong())
        exchange.responseBody.write(response.toByteArray())
        exchange.close()
    }

    fun getAll(exchange: HttpExchange) {
        val carList = carService.getAllCars()
        val response = Json.encodeToString(carList)
        exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
        exchange.responseBody.write(response.toByteArray())
        exchange.close()
    }

    fun getById(exchange: HttpExchange, id: Int) {
        val car: Car? = carService.getCarById(id)
        if (car != null) {
            val response = Json.encodeToString(car)
            exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
            exchange.responseBody.write(response.toByteArray())
        } else {
            exchange.sendResponseHeaders(404, -1)
        }
        exchange.close()
    }

    fun deleteById(exchange: HttpExchange, id: Int) {
        val success = carService.deleteCarById(id)
        if (success) {
            exchange.sendResponseHeaders(204, -1)
        } else {
            exchange.sendResponseHeaders(404, -1)
        }
        exchange.close()
    }

    fun update(exchange: HttpExchange, id: Int) {
        val json = exchange.requestBody.bufferedReader().readText()
        val car = Json.decodeFromString<Car>(json)
        val success = carService.updateCar(id, car)
        if (success) {
            exchange.sendResponseHeaders(200, -1)
        } else {
            exchange.sendResponseHeaders(404, -1)
        }
        exchange.close()
    }
}