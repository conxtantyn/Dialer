package com.constantine.android.ui.screen.main

import androidx.lifecycle.ViewModelProvider
import com.constantine.android.content.ViewModelFactory
import com.constantine.core.config.scope.Page
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainProvider {
    @Binds
    internal abstract fun provideVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Page
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun provideMainActivity(): MainActivity
}
