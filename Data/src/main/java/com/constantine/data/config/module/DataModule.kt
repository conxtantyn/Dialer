package com.constantine.data.config.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DataModule(private val context: Context) {
    @Provides
    internal fun provideContext(): Context = context
}
