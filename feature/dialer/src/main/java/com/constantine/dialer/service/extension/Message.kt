package com.constantine.dialer.service.extension

import android.os.Message
import android.os.Messenger

fun Messenger.dispatch(what: Int) = send(Message.obtain(null, what))

fun MutableList<Messenger>.dispatch(what: Int) {
    forEach { it.send(Message.obtain(null, what)) }
}

fun MutableList<Messenger>.clearWith(what: Int) {
    forEach {
        it.send(Message.obtain(null, what))
        remove(it)
    }
}
