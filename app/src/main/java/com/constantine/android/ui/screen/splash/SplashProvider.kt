package com.constantine.android.ui.screen.splash

import com.constantine.core.config.scope.Scene
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashProvider {
    @Scene
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun provideSplashFragment(): SplashFragment
}
