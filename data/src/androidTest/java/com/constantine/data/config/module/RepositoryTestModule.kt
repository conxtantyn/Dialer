package com.constantine.data.config.module

import com.constantine.domain.repository.ConfigRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class RepositoryTestModule {
    @Provides
    @Singleton
    fun provideConfigRepository(): ConfigRepository = mockk()
}
