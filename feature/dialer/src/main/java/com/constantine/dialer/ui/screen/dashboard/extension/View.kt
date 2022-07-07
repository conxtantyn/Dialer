package com.constantine.dialer.ui.screen.dashboard.extension

import android.widget.CheckBox
import com.constantine.dialer.R

inline var CheckBox.value: Boolean
    get() = isChecked
    set(value) {
        text = if (value) {
            context.getString(R.string.connected)
        } else context.getString(R.string.disconnected)
    }
