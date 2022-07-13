package com.constantine.dialer.config

import com.constantine.data.config.module.DataModule
import com.constantine.data.config.module.RepositoryTestModule
import com.constantine.dialer.server.ServerTestModule
import com.constantine.domain.config.DomainComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        RepositoryTestModule::class,
        ServerTestModule::class
    ]
)
interface DataTestComponent : DomainComponent
