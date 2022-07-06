package com.constantine.dialer.ui.screen.listing

import com.constantine.dialer.config.scope.Dialer
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListingProvider {
    @Dialer
    @ContributesAndroidInjector(modules = [ListingModule::class])
    internal abstract fun provideListingFragment(): ListingFragment
}
