package com.constantine.domain.server.repository

import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.Connection

interface ServerRepository {
    fun isAlive(): Boolean
    fun connect(listener: ConnectionListener)
    fun disconnect()

    interface ConnectionListener {
        fun onConnected(connection: Connection)
        fun onDisconnected(error: ServerException)
    }
}
