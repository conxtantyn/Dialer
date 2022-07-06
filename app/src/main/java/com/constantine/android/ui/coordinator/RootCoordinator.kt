package com.constantine.android.ui.coordinator

import androidx.navigation.NavController
import com.constantine.android.ui.navigation.HomeNavigation
import javax.inject.Inject

class RootCoordinator @Inject constructor(
    private val homeNavigation: HomeNavigation
) : Coordinator {
    fun navigateToHome(controller: NavController) {
        homeNavigation.navigate(controller)
    }
}
