package com.constantine.domain.config

interface DomainComponent {
    interface Injector {
        val domain: DomainComponent
    }
}
