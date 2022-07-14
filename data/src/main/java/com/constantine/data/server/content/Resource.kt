package com.constantine.data.server.content

import com.constantine.data.server.model.ErrorModel
import com.constantine.data.server.scope.Endpoint
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import java.lang.reflect.Method

abstract class Resource {
    private var isInitialize: Boolean = false

    private val resourceMapper: MutableMap<NanoHTTPD.Method,
        MutableMap<String, Method>> = mutableMapOf()

    fun serve(session: NanoHTTPD.IHTTPSession, path: String): NanoHTTPD.Response {
        if (!isInitialize) {
            initialize()
        }
        return resourceMapper[session.method]?.let {
            it.keys.forEach { route ->
                if (match(route, path)) {
                    return invokeRoute(session, it[route]!!)
                }
            }
            null
        } ?: notFoundResponse
    }

    private fun invokeRoute(session: NanoHTTPD.IHTTPSession, method: Method): NanoHTTPD.Response {
        return try {
            method.invoke(this, session) as NanoHTTPD.Response
        } catch (ex: IllegalArgumentException) {
            notFoundResponse
        }
    }

    private fun match(placeholder: String, path: String): Boolean {
        val paths = path.split("/")
        val endpoints = placeholder.split("/")
        if (endpoints.size != paths.size) {
            return false
        }
        endpoints.forEachIndexed { index, str ->
            if (str.indexOf(':') < 0 && str != paths[index]) {
                return false
            }
        }
        return true
    }

    private fun initialize() {
        javaClass.declaredMethods.forEach {
            if (it.isAnnotationPresent(Endpoint::class.java)) {
                it.getAnnotation(Endpoint::class.java)?.let { annotation ->
                    if (resourceMapper[annotation.method] == null) {
                        resourceMapper[annotation.method] = mutableMapOf()
                    }
                    if (it.returnType == NanoHTTPD.Response::class.java) {
                        val method = resourceMapper[annotation.method]!![annotation.value]
                        if (method == null) {
                            resourceMapper[annotation.method]!![annotation.value] = it
                        }
                    }
                }
            }
        }
        isInitialize = true
    }

    companion object {
        const val jsonMimeType: String = "text/json"

        val notFoundResponse: NanoHTTPD.Response = NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT,
            Gson().toJson(ErrorModel("Not Found"))
        )
    }
}
