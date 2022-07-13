package com.constantine.dialer.server

import com.constantine.data.server.data.dao.CallLogDao
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class DatabaseTestModule {
    @Provides
    @Singleton
    fun providesCallLogDao(): CallLogDao = mockk()
}
