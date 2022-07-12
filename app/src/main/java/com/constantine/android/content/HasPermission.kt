package com.constantine.android.content

interface HasPermission {
    fun onRequestPermissions(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}
