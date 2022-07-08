package com.constantine.data.server.repository

import com.constantine.domain.server.exception.ConnectionException
import com.constantine.domain.server.exception.ConnectionTerminatedException
import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.Connection
import com.constantine.domain.server.repository.ServerRepository
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.util.WeakHashMap
import javax.inject.Inject

class HttpdRepository @Inject constructor() : ServerRepository {

    private val listenerMap = WeakHashMap<ServerRepository.ConnectionListener, String>()

    private var httpd: Httpd? = null

    override fun connect(listener: ServerRepository.ConnectionListener) {
        listenerMap[listener] = listener.javaClass.name
        if (httpd == null) {
            try {
                httpd = Httpd()
            } catch (ex: IOException) {
                onDisconnect(ConnectionException())
            }
        }
        listener.onConnected(Connection())
    }

    private fun onDisconnect(ex: ServerException) {
        System.gc()
        httpd?.closeAllConnections()
        listenerMap.keys.forEach {
            it.onDisconnected(ex)
        }
        httpd = null
    }

    override fun disconnect() = onDisconnect(ConnectionTerminatedException())

    private class Httpd : NanoHTTPD(8080) {
        init {
            start(SOCKET_READ_TIMEOUT, false)
        }

        override fun serve(session: IHTTPSession?): Response {
            return newFixedLengthResponse("Hello, world")
        }
    }
}
