package com.constantine.android.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.constantine.core.config.scope.Scene
import com.constantine.core.content.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {
    @Binds
    @Scene
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun provideSplashViewModel(vm: SplashViewModel): ViewModel
}
