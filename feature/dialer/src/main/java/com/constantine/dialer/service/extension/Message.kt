package com.constantine.dialer.service.extension

import android.os.Message
import android.os.Messenger

fun Messenger.dispatch(what: Int) = send(Message.obtain(null, what))

fun Messenger.dispatch(what: Int, data: Any?) {
    send(Message.obtain(null, what, data))
}

fun MutableList<Messenger>.clearWith(what: Int) {
    forEach {
        it.send(Message.obtain(null, what))
        remove(it)
    }
}
