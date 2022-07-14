package com.constantine.data.config

import com.constantine.data.config.module.DataModule
import com.constantine.data.config.module.RepositoryTestModule
import com.constantine.data.server.ServerTestModule
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
