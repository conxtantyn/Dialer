package com.constantine.domain.server.usecase

import com.constantine.domain.server.repository.ServerRepository
import javax.inject.Inject

class IsConnectedUsecase @Inject constructor(
    private val repository: ServerRepository
) {
    fun isAlive(): Boolean = repository.isAlive()
}
