package com.constantine.dialer.ui.screen.home

import androidx.lifecycle.ViewModel
import com.constantine.core.config.scope.Scene
import com.constantine.core.content.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @Binds
    @Scene
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun provideMainViewModel(vm: HomeViewModel): ViewModel
}
