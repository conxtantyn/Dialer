package com.constantine.dialer.service

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceTestModule {
    @ContributesAndroidInjector
    abstract fun providesDialerServiceUnitTest(): DialerServiceUnitTest
}
