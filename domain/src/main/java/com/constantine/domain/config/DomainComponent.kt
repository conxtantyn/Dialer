package com.constantine.domain.config

import com.constantine.domain.repository.ConfigRepository
import com.constantine.domain.server.ServerComponent

interface DomainComponent : ServerComponent {
    fun provideConfigRepository(): ConfigRepository

    interface Injector {
        val domain: DomainComponent
    }
}
