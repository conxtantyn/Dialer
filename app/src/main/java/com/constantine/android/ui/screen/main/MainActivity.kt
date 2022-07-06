package com.constantine.android.ui.screen.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.constantine.android.R
import com.constantine.android.ui.component.BaseActivity

class MainActivity : BaseActivity() {

    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        viewModel = null

        super.onDestroy()
    }
}
