package com.constantine.domain.server.usecase

import com.constantine.domain.server.repository.ServerRepository
import javax.inject.Inject

class ServerConnectionUsecase @Inject constructor(
    private val repository: ServerRepository
) {
    fun connect(listener: ServerRepository.ConnectionListener) {
        repository.connect(listener)
    }
}
