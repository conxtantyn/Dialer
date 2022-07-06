package com.constantine.core.component

import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class SplitApplication : SplitCompatApplication() {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>
}
