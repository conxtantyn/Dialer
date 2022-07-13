package com.constantine.dialer.ui.screen.dashboard

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.constantine.android.content.HasPermission
import com.constantine.android.ui.component.BaseFragment
import com.constantine.dialer.R
import com.constantine.dialer.databinding.FragmentDashboardBinding
import com.constantine.dialer.service.Dialer
import com.constantine.dialer.ui.screen.dashboard.extension.handleAction
import com.google.android.material.snackbar.Snackbar

class DashboardFragment :
    BaseFragment(R.layout.fragment_dashboard),
    ServiceConnection,
    HasPermission {

    private var messenger: Messenger? = null

    private var viewModel: DashboardViewModel? = null

    private var binding: FragmentDashboardBinding? = null

    private var messageService: Messenger? = null

    private var receiver: ServiceBroadcastReceiver? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        receiver = ServiceBroadcastReceiver(this)

        initView()
        initViewModel()
        ServiceBroadcastReceiver.register(requireContext(), receiver!!)

        messenger = Messenger(IncomingHandler(viewModel))

        viewModel?.initialize()
    }

    private fun initView() = updateAddress()

    private fun updateAddress(address: String = "") {
        val uri = "http://$address:${getString(R.integer.port)}"
        binding?.dashboard?.config?.ipTextView?.text = if (address.isValid()) {
            uri
        } else getString(R.string.noConnection)
        if (!address.isValid()) {
            binding?.dashboard?.config?.addressLayout?.setOnClickListener {}
        } else {
            binding?.dashboard?.config?.addressLayout?.setOnClickListener {
                launchBrowser(uri)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)
            .get(DashboardViewModel::class.java).also {
                it.state.observe(viewLifecycleOwner, Observer(::handleViewState))
            }
    }

    private fun handleViewState(state: Dashboard.State) {
        when (state) {
            is Dashboard.State.OnInitialize -> onInitialized(state.isRunning)
            is Dashboard.State.OnStop -> updateControl(false)
            is Dashboard.State.OnRegister -> updateControl(true)
            is Dashboard.State.ConnectionChanged -> updateAddress(state.address)
        }
    }

    private fun launchBrowser(uri: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW).also {
                it.data = uri.toUri()
            }
        )
    }

    private fun onInitialized(isRunning: Boolean) {
        if (isRunning) {
            viewModel?.bindService(requireContext(), this)
        } else {
            updateControl(isRunning)
        }
    }

    fun updateControl(enable: Boolean = false) {
        binding?.dashboard?.config?.serverSwitch?.let {
            it.isChecked = enable
            it.setOnCheckedChangeListener { _, state ->
                handleSwitch(state)
            }
        }
    }

    private fun handleSwitch(state: Boolean) {
        if (!hasRequiredPermissions()) {
            return requestRequiredPermissions()
        }
        toggleService(state)
    }

    private fun hasRequiredPermissions(): Boolean {
        return !mutableListOf(
            requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS),
            requireContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE),
            requireContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG),
            requireContext().checkSelfPermission(Manifest.permission.WRITE_CALL_LOG),
            requireContext().checkSelfPermission(Manifest.permission.WRITE_CONTACTS),
        ).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.add(
                    requireContext()
                        .checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE)
                )
            }
        }.contains(PackageManager.PERMISSION_DENIED)
    }

    private fun requestRequiredPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.FOREGROUND_SERVICE
            ),
            hashCode()
        )
    }

    override fun onRequestPermissions(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == hashCode()) {
            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_DENIED &&
                    !(
                        Build.VERSION.SDK_INT < Build.VERSION_CODES.O &&
                            permission == Manifest.permission.FOREGROUND_SERVICE
                        )
                ) {
                    showSnackbar(getString(R.string.permissionText, permission))
                    binding?.dashboard?.config?.serverSwitch?.setOnCheckedChangeListener(null)
                    updateControl(false)
                    return
                }
            }
            toggleService(true)
        }
    }

    private fun showSnackbar(text: String) {
        binding?.let {
            Snackbar.make(it.root, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun toggleService(enable: Boolean) {
        if (enable) {
            viewModel?.startService(requireContext())
        } else {
            messageService?.dispatch(Dialer.MsgStopService)
        }
    }

    override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
        messageService = Messenger(service).also {
            it.dispatch(Dialer.MsgRegisterClient)
        }
    }

    override fun onServiceDisconnected(className: ComponentName?) {
        messageService = null

        updateControl()
    }

    override fun onDestroyView() {
        messageService?.dispatch(Dialer.MsgUnRegisterClient)

        requireContext().unregisterReceiver(receiver)
        resetMessenger()
        resetComponents()
        super.onDestroyView()
    }

    private fun resetMessenger() {
        messenger = null
        messageService = null
    }

    private fun resetComponents() {
        binding?.dashboard?.config?.serverSwitch?.isVisible = false

        binding = null
        receiver = null
        viewModel = null
    }

    private fun String.isValid(): Boolean {
        return trim(' ').isNotEmpty()
    }

    private fun Messenger.dispatch(what: Int) = handleAction {
        Message.obtain(null, what).also { message ->
            message.replyTo = messenger
            send(message)
        }
    }

    internal class IncomingHandler constructor(
        private val viewModel: DashboardViewModel?
    ) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            viewModel?.handleMessage(msg)
        }
    }

    internal class ServiceBroadcastReceiver constructor(
        private val fragment: DashboardFragment
    ) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Dialer::class.java.name) {
                fragment.onInitialized(true)
            }
        }

        companion object {
            fun register(context: Context, receiver: ServiceBroadcastReceiver) {
                context.registerReceiver(receiver, IntentFilter(Dialer::class.java.name))
            }
        }
    }
}
