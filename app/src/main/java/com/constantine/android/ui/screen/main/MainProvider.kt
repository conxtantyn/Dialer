package com.constantine.android.ui.screen.main

import com.constantine.core.config.scope.Page
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainProvider {
    @Page
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun provideMainActivity(): MainActivity
}
