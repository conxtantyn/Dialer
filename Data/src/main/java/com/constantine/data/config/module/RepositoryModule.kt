package com.constantine.data.config.module

import com.constantine.data.server.repository.HttpdRepository
import com.constantine.domain.server.repository.ServerRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideServerRepository(httpdRepository: HttpdRepository): ServerRepository
}
