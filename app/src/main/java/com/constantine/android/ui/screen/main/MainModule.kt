package com.constantine.android.ui.screen.main

import androidx.lifecycle.ViewModel
import com.constantine.android.ui.screen.splash.SplashProvider
import com.constantine.core.config.scope.Page
import com.constantine.core.content.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [SplashProvider::class])
abstract class MainModule {
    @Binds
    @Page
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideMainViewModel(vm: MainViewModel): ViewModel
}
