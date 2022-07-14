package com.constantine.data.content

import com.constantine.data.config.scope.Endpoint
import fi.iki.elonen.NanoHTTPD
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ResourceUnitTest {

    private var service: TestService = mockk()

    private var resource: TestResource = TestResource(service)

    private var session: NanoHTTPD.IHTTPSession = mockk()

    @Before
    fun onSetup() {
        every { service.handleRequest() } returns NanoHTTPD.newFixedLengthResponse("test...")
    }

    @Test
    fun `test if endpoint exist`() {
        every { session.method } returns NanoHTTPD.Method.GET

        resource.serve(session, "/test")

        verify(exactly = 1) { service.handleRequest() }
    }

    @Test
    fun `test non existent endpoint`() {
        every { session.method } returns NanoHTTPD.Method.GET

        resource.serve(session, "/not-exist")

        verify(exactly = 0) { service.handleRequest() }
    }

    @Test
    fun `test endpoint with variable`() {
        every { session.method } returns NanoHTTPD.Method.GET

        resource.serve(session, "/test/879437")

        verify(exactly = 1) { service.handleRequest() }
    }

    @Test
    fun `test mix endpoint with variable`() {
        every { session.method } returns NanoHTTPD.Method.GET

        resource.serve(session, "/test/879437/unit")

        verify(exactly = 1) { service.handleRequest() }
    }

    @Test
    fun `test endpoint with wrong method signature`() {
        every { session.method } returns NanoHTTPD.Method.GET

        resource.serve(session, "/test/invalid")

        verify(exactly = 0) { service.handleRequest() }
    }

    interface TestService {
        fun handleRequest(): NanoHTTPD.Response
    }

    class TestResource(private val service: TestService) : Resource() {
        @Endpoint(method = NanoHTTPD.Method.GET, value = "/test")
        fun test(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
            return service.handleRequest()
        }

        @Endpoint(method = NanoHTTPD.Method.GET, value = "/test/:id")
        fun testWithPathVariable(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
            return service.handleRequest()
        }

        @Endpoint(method = NanoHTTPD.Method.GET, value = "/test/:uId/unit")
        fun testWithMixedPathVariable(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
            return service.handleRequest()
        }

        @Endpoint(method = NanoHTTPD.Method.GET, value = "/test/invalid")
        fun testInvalidEndpoint(): NanoHTTPD.Response {
            return service.handleRequest()
        }
    }
}
