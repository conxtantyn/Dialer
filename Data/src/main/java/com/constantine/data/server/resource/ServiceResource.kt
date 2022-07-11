package com.constantine.data.server.resource

import com.constantine.data.config.scope.Endpoint
import com.constantine.data.content.Resource
import com.constantine.domain.server.usecase.CallStatusUsecase
import com.constantine.domain.server.usecase.GetCallLogListUsecase
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ServiceResource @Inject constructor(
    private val getCallLogListUsecase: GetCallLogListUsecase,
    private val callStatusUsecase: CallStatusUsecase
) : Resource() {

    @Endpoint(method = NanoHTTPD.Method.GET, value = "/logs")
    fun log(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response = runBlocking {
        NanoHTTPD.newFixedLengthResponse("Log...${getCallLogListUsecase.log()}")
    }

    @Endpoint(method = NanoHTTPD.Method.GET, value = "/status")
    fun status(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse("Status...${callStatusUsecase.status()}")
    }
}
