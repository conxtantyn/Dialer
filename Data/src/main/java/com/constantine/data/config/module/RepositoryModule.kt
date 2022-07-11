package com.constantine.data.config.module

import com.constantine.data.server.repository.CallMonitorRepository
import com.constantine.data.server.repository.HttpdRepository
import com.constantine.domain.server.repository.CallRepository
import com.constantine.domain.server.repository.ServerRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideCallRepository(repository: CallMonitorRepository): CallRepository

    @Binds
    @Singleton
    abstract fun provideServerRepository(repository: HttpdRepository): ServerRepository
}
