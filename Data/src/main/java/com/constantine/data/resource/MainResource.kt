package com.constantine.data.resource

import com.constantine.data.config.scope.Endpoint
import com.constantine.data.content.Resource
import fi.iki.elonen.NanoHTTPD
import javax.inject.Inject

class MainResource @Inject constructor() : Resource() {
    @Endpoint(method = NanoHTTPD.Method.GET, value = "/")
    fun index(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse("Hello, world")
    }
}
