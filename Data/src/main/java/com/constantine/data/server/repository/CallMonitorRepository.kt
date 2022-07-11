package com.constantine.data.server.repository

import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallMonitorRepository @Inject constructor() : CallRepository {
    override fun onStateChanged(state: Int) {}
}
