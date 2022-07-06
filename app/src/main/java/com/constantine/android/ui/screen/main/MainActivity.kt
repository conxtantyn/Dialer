package com.constantine.android.ui.screen.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.constantine.android.R
import com.constantine.android.ui.component.BaseActivity
import com.constantine.core.content.SimpleAndroidInjector
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private var viewModel: MainViewModel? = null

    @Inject
    internal lateinit var simpleAndroidInjector: SimpleAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
            it.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                if (fragment is HasAndroidInjector &&
                    simpleAndroidInjector.maybeInject(fragment)
                ) {
                    return simpleAndroidInjector.injector
                }
            }
        }
        return super.androidInjector()
    }

    override fun onDestroy() {
        viewModel = null

        super.onDestroy()
    }
}
