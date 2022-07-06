package com.constantine.android.ui.screen.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.constantine.android.R
import com.constantine.android.ui.component.BaseActivity
import com.constantine.android.ui.coordinator.RootCoordinator
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    internal lateinit var coordinator: RootCoordinator

    private var viewModel: MainViewModel? = null

    private var hostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hostFragment = supportFragmentManager.findFragmentById(R.id.main) as NavHostFragment
        hostFragment?.let {
            viewModel = ViewModelProvider(this, factory)
                .get(MainViewModel::class.java).also {
                it.event.observe(this, Observer(::handleViewEvent))
            }
        }
    }

    private fun handleViewEvent(event: Main.Event) {
        when (event) {
            is Main.Event.Initialized -> hostFragment?.let {
                coordinator.navigateToHome(it.navController)
            }
        }
    }

    override fun onDestroy() {
        viewModel = null
        hostFragment = null

        super.onDestroy()
    }
}
