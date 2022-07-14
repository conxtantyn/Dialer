package com.constantine.data.server.repository

import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.ConnectionInfo
import com.constantine.domain.server.repository.ServerRepository
import io.mockk.every
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
        repository = HttpdRepository(mockk())
        listener = object : ServerRepository.ConnectionListener {
            override fun onConnected(connection: ConnectionInfo) {
                mockListener.onConnected(connection)
            }

            override fun onDisconnected(error: ServerException) {
                mockListener.onDisconnected(error)
            }
        }
    }

    @Test
    fun `when listener service is destroyed by the os`() {
        every { mockListener.onConnected(any()) } answers {}

        listener?.let { repository.connect("", 8080, it) }
        listener = null
        repository.disconnect()

        verify(exactly = 0) { mockListener.onDisconnected(any()) }
    }
}
