package com.constantine.dialer.ui.screen.home

import androidx.lifecycle.ViewModelProvider
import com.constantine.android.content.ViewModelFactory
import com.constantine.core.config.scope.Scene
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeProvider {
    @Binds
    internal abstract fun provideVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Scene
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun provideHomeFragment(): HomeFragment
}
