package com.constantine.dialer.ui.screen.listing

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListingProvider {
    @ContributesAndroidInjector(modules = [ListingModule::class])
    internal abstract fun provideListingFragment(): ListingFragment
}
