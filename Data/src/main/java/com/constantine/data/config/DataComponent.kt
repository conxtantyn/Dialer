package com.constantine.data.config

import com.constantine.data.config.module.DataModule
import com.constantine.data.config.module.RepositoryModule
import com.constantine.data.config.module.ResourceModule
import com.constantine.domain.config.DomainComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        RepositoryModule::class,
        ResourceModule::class
    ]
)
internal interface DataComponent : DomainComponent
