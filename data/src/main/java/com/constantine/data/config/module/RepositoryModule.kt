package com.constantine.data.config.module

import com.constantine.data.repository.ConfigurationRepository
import com.constantine.domain.repository.ConfigRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideConfigRepository(repository: ConfigurationRepository): ConfigRepository
}
