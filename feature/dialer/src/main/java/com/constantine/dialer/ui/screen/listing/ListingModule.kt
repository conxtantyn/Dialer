package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.ViewModel
import com.constantine.core.content.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListingModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    internal abstract fun provideListingViewModel(vm: ListingViewModel): ViewModel
}
