package com.constantine.dialer.server

import com.constantine.data.server.ResourceModule
import com.constantine.data.server.repository.CallMonitorRepository
import com.constantine.data.server.repository.HttpdRepository
import com.constantine.domain.server.repository.CallRepository
import com.constantine.domain.server.repository.ServerRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module(
    includes = [
        ResourceModule::class,
        DatabaseTestModule::class
    ]
)
class ServerTestModule {
    @Provides
    @Singleton
    fun provideCallRepository(repository: CallMonitorRepository): CallRepository = mockk()

    @Provides
    @Singleton
    fun provideServerRepository(repository: HttpdRepository): ServerRepository = mockk()
}
