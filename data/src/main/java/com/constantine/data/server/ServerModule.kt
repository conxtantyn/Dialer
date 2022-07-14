package com.constantine.data.server

import com.constantine.data.server.repository.CallMonitorRepository
import com.constantine.data.server.repository.HttpdRepository
import com.constantine.domain.server.repository.CallRepository
import com.constantine.domain.server.repository.ServerRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        ResourceModule::class,
        DatabaseModule::class
    ]
)
abstract class ServerModule {
    @Binds
    @Singleton
    abstract fun provideCallRepository(repository: CallMonitorRepository): CallRepository

    @Binds
    @Singleton
    abstract fun provideServerRepository(repository: HttpdRepository): ServerRepository
}
