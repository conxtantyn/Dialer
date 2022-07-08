package com.constantine.domain.config

import com.constantine.domain.server.ServerComponent

interface DomainComponent : ServerComponent {
    interface Injector {
        val domain: DomainComponent
    }
}
