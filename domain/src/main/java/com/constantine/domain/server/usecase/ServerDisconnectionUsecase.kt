package com.constantine.domain.server.usecase

import com.constantine.domain.server.repository.ServerRepository
import javax.inject.Inject

class ServerDisconnectionUsecase @Inject constructor(
    private val repository: ServerRepository
) {
    fun disconnect() = repository.disconnect()
}
