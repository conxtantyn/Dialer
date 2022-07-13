package com.constantine.dialer.ui.screen.dashboard.listing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.constantine.android.ui.component.BaseFragment
import com.constantine.dialer.R
import com.constantine.dialer.databinding.FragmentListingBinding
import com.constantine.dialer.listener.CallManager
import com.constantine.dialer.listener.implementation.CallManagerImplementation
import com.constantine.dialer.ui.screen.dashboard.Dashboard
import com.constantine.dialer.ui.screen.dashboard.DashboardViewModel
import javax.inject.Inject

class ListingFragment : BaseFragment(R.layout.fragment_listing), CallManager.CallListener {

    private var viewModel: ListingViewModel? = null

    private var dashboardViewModel: DashboardViewModel? = null

    private var binding: FragmentListingBinding? = null

    private var callManager: CallManager? = null

    @Inject
    internal lateinit var adapter: ListingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListingBinding.bind(view)
        callManager = CallManagerImplementation().also {
            it.registerService(requireContext(), this)
        }
        initViewModel()
        initView()
    }

    private fun refreshLog() {
        if (hasRequiredPermissions()) {
            binding?.progressBar?.isVisible = adapter.itemCount == 0
            viewModel?.refreshLog()
        } else {
            displayEmptyView()
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return !mutableListOf(
            requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS),
            requireContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE),
            requireContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG),
            requireContext().checkSelfPermission(Manifest.permission.WRITE_CALL_LOG),
            requireContext().checkSelfPermission(Manifest.permission.WRITE_CONTACTS),
        ).contains(PackageManager.PERMISSION_DENIED)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)
            .get(ListingViewModel::class.java).also {
                it.state.observe(viewLifecycleOwner, Observer(::handleViewState))
            }
        parentFragment?.let {
            dashboardViewModel = ViewModelProvider(it, factory)
                .get(DashboardViewModel::class.java).also { vm ->
                    vm.state.observe(viewLifecycleOwner, Observer(::handleViewState))
                }
        }
    }

    private fun handleViewState(state: Listing.State) {
        when (state) {
            is Listing.State.DisplayListing -> {
                if (adapter.itemCount == 0 && state.list.isEmpty()) {
                    displayEmptyView()
                } else {
                    adapter.submit(state.list)
                    binding?.progressBar?.visibility = View.GONE
                    binding?.viewSwitcher?.let {
                        binding?.contentLayout?.let { recyclerView ->
                            it.displayedChild = it.indexOfChild(recyclerView)
                        }
                    }
                }
            }
        }
    }

    private fun displayEmptyView() {
        binding?.viewSwitcher?.let {
            binding?.emptyView?.let { emptyView ->
                it.displayedChild = it.indexOfChild(emptyView.root)
            }
        }
    }

    private fun initView() {
        binding?.recyclerView?.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
        }
    }

    private fun handleViewState(state: Dashboard.State) {
        if (state is Dashboard.State.OnRegister && adapter.itemCount == 0) {
            refreshLog()
        }
    }

    override fun onCallStateChanged(state: Int, number: String) {
        if (state == TelephonyManager.CALL_STATE_IDLE) {
            refreshLog()
        }
    }

    override fun onResume() {
        refreshLog()
        super.onResume()
    }

    override fun onDestroyView() {
        callManager?.unRegisterService(requireContext())

        binding = null
        viewModel = null
        callManager = null

        super.onDestroyView()
    }
}
