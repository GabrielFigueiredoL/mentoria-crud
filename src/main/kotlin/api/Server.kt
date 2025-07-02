package org.gabrielfigueiredol.api

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

object Server {
    val server: HttpServer = HttpServer.create(InetSocketAddress(8080), 0).apply {
        executor = null
    }
}