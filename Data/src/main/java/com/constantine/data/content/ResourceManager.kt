package com.constantine.data.content

import android.net.Uri
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val resources: Map<String, @JvmSuppressWildcards Resource>
) {
    fun getResource(uri: Uri): Route? {
        val route = uri.path?.split("/") ?: arrayListOf("")
        val path = route.subList(2, route.size).joinToString("/")

        return resources["/${route[1]}"]?.let { Route("/${path.trim('/')}", it) }
    }
}
