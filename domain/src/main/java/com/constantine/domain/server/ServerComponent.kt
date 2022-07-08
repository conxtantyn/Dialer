package com.constantine.domain.server

import com.constantine.domain.server.repository.ServerRepository

interface ServerComponent {
    fun provideServerRepository(): ServerRepository
}
