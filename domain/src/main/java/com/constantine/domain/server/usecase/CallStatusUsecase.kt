package com.constantine.domain.server.usecase

import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallStatusUsecase @Inject constructor(
    private val repository: CallRepository
) {
    fun status() = repository.getStatus()
}
