package com.constantine.domain.server

import com.constantine.domain.server.repository.CallRepository
import com.constantine.domain.server.repository.ServerRepository

interface ServerComponent {
    fun provideCallRepository(): CallRepository
    fun provideServerRepository(): ServerRepository
}
