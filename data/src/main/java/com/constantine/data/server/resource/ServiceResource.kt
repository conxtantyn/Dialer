package com.constantine.data.server.resource

import com.constantine.data.server.content.Resource
import com.constantine.data.server.model.ErrorModel
import com.constantine.data.server.scope.Endpoint
import com.constantine.domain.server.usecase.CallLogListUsecase
import com.constantine.domain.server.usecase.CallStatusUsecase
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ServiceResource @Inject constructor(
    private val getCallLogListUsecase: CallLogListUsecase,
    private val callStatusUsecase: CallStatusUsecase
) : Resource() {

    @Endpoint(method = NanoHTTPD.Method.GET, value = "/logs")
    fun log(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response = runBlocking {
        NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            jsonMimeType,
            Gson().toJson(getCallLogListUsecase.log())
        )
    }

    @Endpoint(method = NanoHTTPD.Method.GET, value = "/status")
    fun status(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        val status = callStatusUsecase.status()
        return NanoHTTPD.newFixedLengthResponse(
            if (status == null) {
                NanoHTTPD.Response.Status.BAD_REQUEST
            } else NanoHTTPD.Response.Status.OK,
            jsonMimeType,
            if (status == null) {
                Gson().toJson(ErrorModel("No active call"))
            } else Gson().toJson(status)
        )
    }
}
