package com.constantine.android.ui.component

import androidx.annotation.LayoutRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment(@LayoutRes layout: Int) : DaggerFragment(layout)
