package org.gabrielfigueiredol

import org.gabrielfigueiredol.api.CarApi
import org.gabrielfigueiredol.api.Server

fun main() {
    val server = Server.server
    val carApi = CarApi(server)
    carApi.initCarApi()

    server.start()
    println("Servidor iniciado na porta 8000")
}
