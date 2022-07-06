package com.constantine.core.config.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class CoreModule {
    @Provides
    fun provideContext(application: Application): Context = application
}
