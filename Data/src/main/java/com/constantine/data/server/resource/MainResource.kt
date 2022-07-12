package com.constantine.data.server.resource

import com.constantine.data.config.scope.Endpoint
import com.constantine.data.content.Resource
import com.constantine.domain.usecase.InstallTimestampUsecase
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import java.util.Date
import javax.inject.Inject

class MainResource @Inject constructor(
    private val installTimestampUsecase: InstallTimestampUsecase
) : Resource() {
    @Endpoint(method = NanoHTTPD.Method.GET, value = "/")
    fun index(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        val host = session.headers["host"] ?: ""
        val date = Date(installTimestampUsecase.timestamp())
        val response = mapOf(
            Pair("start", date),
            Pair(
                "services",
                listOf<Map<String, Any>>(
                    mapOf(
                        Pair("name", "status"),
                        Pair("uri", "http://${host.trim('/')}/services/status")
                    ),
                    mapOf(
                        Pair("name", "log"),
                        Pair("uri", "http://${host.trim('/')}/services/logs")
                    )
                )
            ),
        )
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            jsonMimeType,
            Gson().toJson(response)
        )
    }
}
