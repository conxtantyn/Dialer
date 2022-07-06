package com.constantine.core.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notLet(block: (MutableLiveData<T>) -> Unit) {
    if (this.value == null) block.invoke(this)
}
