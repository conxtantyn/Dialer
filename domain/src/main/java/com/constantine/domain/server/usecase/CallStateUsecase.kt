package com.constantine.domain.server.usecase

import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallStateUsecase @Inject constructor(
    private val repository: CallRepository
) {
    fun onChange(state: Int, number: String) = repository.onStateChanged(state, number)
}
