package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.ViewModel
import com.constantine.core.content.ViewModelKey
import com.constantine.dialer.config.scope.Dialer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListingModule {
    @Binds
    @Dialer
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    internal abstract fun provideListingViewModel(vm: ListingViewModel): ViewModel
}
