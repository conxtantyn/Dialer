package com.constantine.dialer.service

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ServiceTestRule
import com.constantine.dialer.DialerApplication
import com.constantine.domain.server.repository.ServerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DialerServiceUnitTest {

    @get:Rule
    internal val serviceRule = ServiceTestRule()

    private val callback: Handler.Callback = mockk()

    private lateinit var messager: Messenger

    @Inject
    internal lateinit var serverRepository: ServerRepository

    @Before
    fun setup() {
        every { callback.handleMessage(any()) } answers { true }

        messager = Messenger(Handler(Looper.getMainLooper(), callback))

        (ApplicationProvider.getApplicationContext() as DialerApplication)
            .androidInjector().inject(this)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.constantine.android", appContext.packageName)
    }

    @Test
    @Throws(TimeoutException::class)
    fun testServiceBound() {
        every { serverRepository.disconnect() } answers { }

        val binder = serviceRule.bindService(
            Intent(
                ApplicationProvider.getApplicationContext(),
                DialerService::class.java
            )
        )
        Messenger(binder).dispatch(Dialer.MsgRegisterClient)

        verify(atLeast = 1) { callback.handleMessage(any()) }
    }

    private fun Messenger.dispatch(what: Int) {
        try {
            Message.obtain(null, what).also { message ->
                message.replyTo = messager
                send(message)
            }
        } catch (ex: RemoteException) { }
    }
}
