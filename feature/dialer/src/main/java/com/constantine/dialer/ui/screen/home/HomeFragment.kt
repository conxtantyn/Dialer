package com.constantine.dialer.ui.screen.home

import com.constantine.android.content.HasPermission
import com.constantine.android.ui.component.BaseFragment
import com.constantine.dialer.R

class HomeFragment : BaseFragment(R.layout.fragment_home), HasPermission {
    override fun onRequestPermissions(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        childFragmentManager.findFragmentById(R.id.nav_host_home_fragment)?.let {
            it.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                if (fragment is HasPermission) {
                    fragment.onRequestPermissions(requestCode, permissions, grantResults)
                    return
                }
            }
        }
    }
}
