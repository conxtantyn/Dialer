package com.constantine.data.server.repository

import com.constantine.domain.server.exception.ConnectionException
import com.constantine.domain.server.exception.ConnectionTerminatedException
import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.ConnectionInfo
import com.constantine.domain.server.repository.ServerRepository
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.util.WeakHashMap
import javax.inject.Inject

class HttpdRepository @Inject constructor() : ServerRepository {

    private val listenerMap = WeakHashMap<ServerRepository.ConnectionListener, String>()

    private var httpd: Httpd? = null

    private var thread: Thread? = null

    override fun isAlive(): Boolean = httpd?.isAlive ?: false

    override fun connect(host: String, port: Int, listener: ServerRepository.ConnectionListener) {
        listenerMap[listener] = listener.javaClass.name
        if (httpd == null) {
            try {
                httpd = Httpd(host, port)
                thread = createWatcher()

                thread?.start()
                httpd?.getConnection()?.let { listener.onConnected(it) }
            } catch (ex: IOException) {
                onDisconnect(ConnectionException())
            }
        }
    }

    private fun createWatcher(): Thread {
        return Thread {
            while (isAlive()) {
                try {
                    Thread.sleep(10L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            onDisconnect(ConnectionException())
        }
    }

    private fun onDisconnect(ex: ServerException) {
        System.gc()
        httpd?.closeAllConnections()
        listenerMap.keys.forEach {
            it.onDisconnected(ex)
        }
        listenerMap.clear()
        httpd = null
        thread = null
    }

    override fun disconnect() = onDisconnect(ConnectionTerminatedException())

    private class Httpd(private val host: String, port: Int) : NanoHTTPD(port) {
        init {
            start(SOCKET_READ_TIMEOUT, false)
        }

        override fun serve(session: IHTTPSession?): Response {
            return newFixedLengthResponse("Hello, world")
        }

        fun getConnection(): ConnectionInfo {
            return ConnectionInfo(
                host = host,
                port = listeningPort
            )
        }
    }
}
