package com.constantine.android.ui.screen.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.constantine.android.R
import com.constantine.android.databinding.FragmentSplashBinding
import com.constantine.android.ui.component.BaseFragment
import com.constantine.android.ui.screen.main.MainViewModel

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private var isLoading: Boolean = true

    private var animationSpeed: Long = 500

    private var viewModel: SplashViewModel? = null

    private var mainViewModel: MainViewModel? = null

    private var binding: FragmentSplashBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSplashBinding.bind(view).also {
            initViewModel(it)
            initAnimation(it)

            viewModel?.initialize()
        }
    }

    private fun initViewModel(binding: FragmentSplashBinding) {
        viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java).also {
            it.state.observe(viewLifecycleOwner, Observer(::handleViewState))
            it.event.observe(viewLifecycleOwner, Observer(::handleViewEvent))
        }
        mainViewModel = ViewModelProvider(requireActivity(), factory)
            .get(MainViewModel::class.java)
    }

    private fun handleViewState(state: Splash.State) {
        isLoading = when (state) {
            is Splash.State.Loading -> true
            is Splash.State.Initialized -> {
                false
            }
        }
    }

    private fun handleViewEvent(event: Splash.Event) {
        when (event) {
            is Splash.Event.AnimationEnd -> if (isLoading) {
                restartLogoAnimation()
            } else {
                mainViewModel?.initialize()
            }
        }
    }

    private fun restartLogoAnimation() {
        binding?.splashLogoAnimation?.apply {
            progress = 0f
            playAnimation()
        }
    }

    private fun initAnimation(binding: FragmentSplashBinding) {
        binding.splashLogoAnimation.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                viewModel?.animate(animationSpeed)
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        viewModel = null
        mainViewModel = null

        super.onDestroyView()
    }
}
