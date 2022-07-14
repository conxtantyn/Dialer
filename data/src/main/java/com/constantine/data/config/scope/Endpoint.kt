package com.constantine.data.config.scope

import fi.iki.elonen.NanoHTTPD

@Retention(AnnotationRetention.RUNTIME)
annotation class Endpoint(val method: NanoHTTPD.Method, val value: String)
