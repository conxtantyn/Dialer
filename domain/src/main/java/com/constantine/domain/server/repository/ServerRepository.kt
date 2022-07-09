package com.constantine.domain.server.repository

import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.ConnectionInfo

interface ServerRepository {
    fun isAlive(): Boolean
    fun connect(host: String, port: Int, listener: ConnectionListener)
    fun disconnect()

    interface ConnectionListener {
        fun onConnected(connection: ConnectionInfo)
        fun onDisconnected(error: ServerException)
    }
}
