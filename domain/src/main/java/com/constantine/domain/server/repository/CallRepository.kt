package com.constantine.domain.server.repository

interface CallRepository {
    fun onStateChanged(state: Int)
}
