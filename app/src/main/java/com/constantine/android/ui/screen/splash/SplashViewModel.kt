package com.constantine.android.ui.screen.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.constantine.core.component.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SplashViewModel @Inject constructor() : ViewModel(), Splash.ViewModel {
    private val mutableState = MutableLiveData<Splash.State>()

    private val mutableEvent = SingleLiveEvent<Splash.Event>()

    override val state: LiveData<Splash.State> get() = mutableState

    override val event: LiveData<Splash.Event> get() = mutableEvent

    override fun animate(duration: Long) {
        viewModelScope.launch {
            delay(duration)
            withContext(Dispatchers.Main) {
                mutableEvent.value = Splash.Event.AnimationEnd
            }
        }
    }

    override fun initialize() {
        viewModelScope.launch {
            // fake network request
            delay(3000)
            mutableState.value = Splash.State.Initialized
        }
    }
}
