package com.constantine.dialer.service.extension

import android.net.Uri

inline val Uri.domain: String get() = host ?: ""
