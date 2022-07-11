package com.constantine.data.resource

import com.constantine.data.config.scope.Endpoint
import com.constantine.data.content.Resource
import fi.iki.elonen.NanoHTTPD
import javax.inject.Inject

class ServiceResource @Inject constructor() : Resource() {
    @Endpoint(method = NanoHTTPD.Method.GET, value = "/log")
    fun log(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse("Log...")
    }

    @Endpoint(method = NanoHTTPD.Method.GET, value = "/status")
    fun status(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse("Status...")
    }
}
