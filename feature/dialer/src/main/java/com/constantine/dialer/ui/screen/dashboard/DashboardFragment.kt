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
import android.widget.Toast.LENGTH_LONG
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
import com.constantine.dialer.ui.screen.dashboard.extension.value
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

        initViewModel()
        ServiceBroadcastReceiver.register(requireContext(), receiver!!)

        messenger = Messenger(IncomingHandler(viewModel))

        viewModel?.initialize()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)
            .get(DashboardViewModel::class.java).also {
                it.state.observe(viewLifecycleOwner, Observer(::handleViewState))
                it.event.observe(viewLifecycleOwner, Observer(::handleViewEvent))
            }
    }

    private fun handleViewState(state: Dashboard.State) {
        when (state) {
            is Dashboard.State.OnInitialize -> onInitialized(state.isRunning)
            is Dashboard.State.OnStop -> updateControl(false)
            is Dashboard.State.OnRegister -> updateControl(true)
            is Dashboard.State.ConnectionChanged -> {
                val address = "http://${state.address}:${getString(R.integer.port)}"
                binding?.ipTextView?.text = address
                binding?.ipTextView?.isVisible = state.address.trim(' ').isNotEmpty()
                binding?.ipTextView?.setOnClickListener {
                    launchBrowser(address)
                }
            }
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

    private fun handleViewEvent(event: Dashboard.Event) {}

    fun updateControl(enable: Boolean = false) {
        binding?.serverSwitch?.let {
            it.value = enable
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
        binding?.serverSwitch?.text = getString(R.string.processing)
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
                    binding?.serverSwitch?.setOnCheckedChangeListener { _, _ -> }
                    updateControl(false)
                    return
                }
            }
            toggleService(true)
        }
    }

    private fun showSnackbar(text: String) {
        binding?.let {
            Snackbar.make(it.root, text, LENGTH_LONG).show()
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
        binding = null
        receiver = null
        viewModel = null

        binding?.serverSwitch?.isVisible = false
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
