package com.constantine.dialer.ui.screen.dashboard

import android.content.Context
import android.content.ServiceConnection
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.constantine.core.component.SingleLiveEvent
import com.constantine.dialer.service.Dialer
import com.constantine.dialer.usecase.BindServiceUsecase
import com.constantine.dialer.usecase.ServiceStateUsecase
import com.constantine.dialer.usecase.StartServiceUsecase
import javax.inject.Inject

internal class DashboardViewModel @Inject constructor(
    private val serviceStateUsecase: ServiceStateUsecase,
    private val startServiceUsecase: StartServiceUsecase,
    private val bindServiceUsecase: BindServiceUsecase
) : ViewModel(), Dashboard.ViewModel {
    private val mutableState = MutableLiveData<Dashboard.State>()

    private val mutableEvent = SingleLiveEvent<Dashboard.Event>()

    override val state: LiveData<Dashboard.State> get() = mutableState

    override val event: LiveData<Dashboard.Event> get() = mutableEvent

    override fun initialize() {
        mutableState.value = Dashboard.State.OnInitialize(serviceStateUsecase.get())
    }

    override fun startService(context: Context) {
        if (!serviceStateUsecase.get()) {
            startServiceUsecase.start(context)
        }
    }

    override fun bindService(context: Context, connection: ServiceConnection) {
        bindServiceUsecase.bind(context, connection)
    }

    override fun handleMessage(message: Message) {
        when (message.what) {
            Dialer.MsgStopService -> {
                mutableState.value = Dashboard.State.OnStop
                mutableState.value = Dashboard.State.ConnectionChanged("")
            }
            Dialer.MsgRegisterClient -> mutableState.value = Dashboard.State.OnRegister
            Dialer.MsgConnectionChange -> {
                message.obj?.let {
                    mutableState.value = Dashboard.State.ConnectionChanged(it.toString())
                }
            }
        }
    }
}
