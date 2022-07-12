package com.constantine.dialer.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import androidx.core.app.NotificationCompat
import com.constantine.dialer.R
import com.constantine.dialer.listener.CallManager
import com.constantine.dialer.listener.implementation.CallManagerImplementation
import com.constantine.dialer.service.Dialer.MsgCallStateChange
import com.constantine.dialer.service.extension.clearWith
import com.constantine.dialer.service.extension.connectivity
import com.constantine.dialer.service.extension.createNotificationChannel
import com.constantine.dialer.service.extension.dispatch
import com.constantine.dialer.service.extension.domain
import com.constantine.dialer.util.getIPAddress
import com.constantine.domain.server.exception.ConnectionException
import com.constantine.domain.server.exception.ServerException
import com.constantine.domain.server.model.ConnectionInfo
import com.constantine.domain.server.repository.ServerRepository
import com.constantine.domain.server.usecase.CallStateUsecase
import com.constantine.domain.server.usecase.ServerConnectionUsecase
import com.constantine.domain.server.usecase.ServerDisconnectionUsecase
import dagger.android.AndroidInjection
import javax.inject.Inject

internal class DialerService :
    Service(),
    CallManager.CallListener,
    ServerRepository.ConnectionListener {

    private val channelId: Int = 1

    private val connectionDelay: Long = 500

    private val clients: MutableList<Messenger> = mutableListOf()

    private val messenger: Messenger = Messenger(IncomingHandler(this))

    private val callManager: CallManager = CallManagerImplementation()

    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        NetworkCallbackHandler(uri.domain, clients)
    }

    @Inject
    internal lateinit var uri: Uri

    @Inject
    internal lateinit var callStateusecase: CallStateUsecase

    @Inject
    internal lateinit var connectionUsecase: ServerConnectionUsecase

    @Inject
    internal lateinit var disconnectionUsecase: ServerDisconnectionUsecase

    override fun onCreate() {
        super.onCreate()
        initNotification()

        SERVICE_STATE = true

        AndroidInjection.inject(this)
        baseContext.sendBroadcast(Intent(Dialer::class.java.name))

        attachListeners()
    }

    private fun initNotification() {
        val name = getString(R.string.channelName)
        val title = getString(R.string.channelTitle)
        val notification: Notification = NotificationCompat.Builder(
            baseContext,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(DialerService::class.java.name, name)
            } else ""
        )
            .setContentTitle(title)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(channelId, notification)
    }

    private fun attachListeners() {
        callManager.registerService(this, this)
        connectivity.registerNetworkCallback(
            NetworkRequest
                .Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build(),
            networkCallback
        )
    }

    override fun onCallStateChanged(state: Int, number: String) {
        callStateusecase.onChange(state, number)
        clients.dispatch(MsgCallStateChange, state)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            try {
                Thread.sleep(connectionDelay)
            } catch (e: InterruptedException) { }
            connectionUsecase.connect(uri.domain, uri.port, this)
        }.start()
        return START_STICKY
    }

    override fun onConnected(connection: ConnectionInfo) {}

    override fun onDisconnected(error: ServerException) {
        if (error is ConnectionException) {
            onStop()
        }
    }

    override fun onBind(intent: Intent?): IBinder = messenger.binder

    private fun onRegister(messenger: Messenger) {
        clients.add(messenger)
        messenger.dispatch(Dialer.MsgRegisterClient)
    }

    private fun onUnRegister(messenger: Messenger) = clients.remove(messenger)

    private fun onStop() = stopService(baseContext)

    override fun onDestroy() {
        disconnect()

        clients.clearWith(Dialer.MsgStopService)
        callManager.unRegisterService(this)
        connectivity.unregisterNetworkCallback(networkCallback)

        super.onDestroy()
    }

    private fun disconnect() {
        SERVICE_STATE = false
        disconnectionUsecase.disconnect()
    }

    private class IncomingHandler(
        private val service: DialerService
    ) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Dialer.MsgStopService -> service.onStop()
                Dialer.MsgRegisterClient -> msg.replyTo?.let {
                    service.onRegister(it)
                }
                Dialer.MsgUnRegisterClient -> msg.replyTo?.let {
                    service.onUnRegister(it)
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private class NetworkCallbackHandler constructor(
        private val host: String,
        private val clients: MutableList<Messenger>
    ) : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val address = getIPAddress() ?: host

            clients.dispatch(Dialer.MsgConnectionChange, address)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            clients.dispatch(Dialer.MsgConnectionChange, host)
            super.onLost(network)
        }
    }

    companion object {
        private var SERVICE_STATE = false

        val IS_SERVICE_RUNNING get() = SERVICE_STATE

        fun stopService(context: Context) {
            context.stopService(Intent(context, DialerService::class.java))
        }

        fun startService(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(Intent(context, DialerService::class.java))
            } else {
                context.startService(Intent(context, DialerService::class.java))
            }
        }

        fun bindService(context: Context, connection: ServiceConnection) {
            context.bindService(
                Intent(context, DialerService::class.java), connection, 0
            )
        }
    }
}
