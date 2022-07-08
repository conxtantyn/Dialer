package com.constantine.data.server.repository

import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.Connection
import com.constantine.domain.server.repository.ServerRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class HttpdRepositoryUnitTest {

    private lateinit var repository: HttpdRepository

    private var listener: ServerRepository.ConnectionListener? = null

    private var mockListener = mockk<ServerRepository.ConnectionListener>()

    @Before
    fun onSetup() {
        repository = HttpdRepository()
        listener = object : ServerRepository.ConnectionListener {
            override fun onConnected(connection: Connection) {
                mockListener.onConnected(connection)
            }

            override fun onDisconnected(error: ServerException) {
                mockListener.onDisconnected(error)
            }
        }
    }

    @Test
    fun `when listener service is destroyed by the os`() {
        listener?.let { repository.connect(it) }
        listener = null
        repository.disconnect()

        verify(exactly = 0) { mockListener.onDisconnected(any()) }
    }
}
