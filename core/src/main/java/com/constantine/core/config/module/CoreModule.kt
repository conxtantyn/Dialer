package com.constantine.core.config.module

import android.content.Context
import com.constantine.core.component.SplitApplication
import dagger.Module
import dagger.Provides

@Module
class CoreModule {
    @Provides
    fun provideContext(application: SplitApplication): Context = application
}
