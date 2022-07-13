package com.constantine.dialer.ui.screen.listing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.constantine.android.ui.component.BaseFragment
import com.constantine.dialer.R
import com.constantine.dialer.databinding.FragmentListingBinding
import javax.inject.Inject

class ListingFragment : BaseFragment(R.layout.fragment_listing) {

    private var viewModel: ListingViewModel? = null

    private var binding: FragmentListingBinding? = null

    @Inject
    internal lateinit var adapter: ListingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListingBinding.bind(view)

        initViewModel()
        initView()

        refreshLog()
    }

    private fun refreshLog() {
        if (hasRequiredPermissions()) {
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
    }

    private fun handleViewState(state: Listing.State) {
        when (state) {
            is Listing.State.DisplayListing -> {
                if (adapter.itemCount == 0 && state.list.isEmpty()) {
                    displayEmptyView()
                } else {
                    adapter.submit(state.list)
                    binding?.viewSwitcher?.let {
                        binding?.recyclerView?.let { recyclerView ->
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

    override fun onResume() {
        refreshLog()
        super.onResume()
    }

    override fun onDestroyView() {
        binding = null
        viewModel = null

        super.onDestroyView()
    }
}
