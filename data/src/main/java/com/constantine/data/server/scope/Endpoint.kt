package com.constantine.data.server.scope

import fi.iki.elonen.NanoHTTPD

@Retention(AnnotationRetention.RUNTIME)
annotation class Endpoint(val method: NanoHTTPD.Method, val value: String)
