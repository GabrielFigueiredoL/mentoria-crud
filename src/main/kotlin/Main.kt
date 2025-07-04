package org.gabrielfigueiredol

import org.gabrielfigueiredol.api.CarApi
import org.gabrielfigueiredol.api.ClientApi
import org.gabrielfigueiredol.api.Server
import org.gabrielfigueiredol.api.TransactionApi

fun main() {
    val server = Server.server
    val carApi = CarApi(server)
    val clientApi = ClientApi(server)
    val transactionApi = TransactionApi(server)
    carApi.initCarApi()
    clientApi.initClientApi()
    transactionApi.initTransactionApi()

    server.start()
    println("Servidor iniciado na porta 8000")
}
