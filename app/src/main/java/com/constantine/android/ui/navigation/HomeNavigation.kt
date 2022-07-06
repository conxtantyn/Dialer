package com.constantine.android.ui.navigation

import androidx.navigation.NavController
import com.constantine.android.R
import javax.inject.Inject

class HomeNavigation @Inject constructor() : Navigation {
    fun navigate(controller: NavController) {
        controller.navigate(R.id.action_splashFragment_to_home)
    }
}
