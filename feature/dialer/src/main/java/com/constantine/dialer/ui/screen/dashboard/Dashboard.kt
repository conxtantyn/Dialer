package com.constantine.dialer.ui.screen.dashboard

import android.content.Context
import android.content.ServiceConnection
import android.os.Message
import androidx.lifecycle.LiveData

class Dashboard {
    internal sealed class State {
        object OnStop : State()
        data class OnRegister(val address: String) : State()

        data class OnInitialize(val isRunning: Boolean) : State()
        data class ConnectionChanged(val address: String) : State()
    }
    internal sealed class Event
    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>

        fun initialize()

        fun startService(context: Context)

        fun bindService(context: Context, connection: ServiceConnection)

        fun handleMessage(message: Message)
    }
}
