package com.constantine.dialer.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import com.constantine.core.content.ViewModelKey
import com.constantine.dialer.config.scope.Dialer
import com.constantine.dialer.ui.screen.dashboard.listing.ListingProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ListingProvider::class
    ]
)
abstract class DashboardModule {
    @Binds
    @Dialer
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    internal abstract fun provideDashboardViewModel(vm: DashboardViewModel): ViewModel
}
