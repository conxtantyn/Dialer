package com.constantine.data.server.util

import android.net.Uri
import com.constantine.data.server.content.Resource
import com.constantine.data.server.content.Route
import javax.inject.Inject

class ResourceUtil @Inject constructor(
    private val resources: Map<String, @JvmSuppressWildcards Resource>
) {
    fun getResource(uri: Uri): Route? {
        val route = uri.path?.split("/") ?: arrayListOf("")
        val path = route.subList(2, route.size).joinToString("/")

        return resources["/${route[1]}"]?.let { Route("/${path.trim('/')}", it) }
    }
}
