package com.constantine.android.ui.component

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layout: Int) : DaggerFragment(layout) {
    @Inject
    protected lateinit var factory: ViewModelProvider.Factory
}
