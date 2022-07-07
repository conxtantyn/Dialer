package com.constantine.dialer.service.extension

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.constantine.dialer.R

@RequiresApi(Build.VERSION_CODES.O)
fun Service.createNotificationChannel(channelId: String, channelName: String): String {
    val channel = NotificationChannel(
        channelId,
        channelName, NotificationManager.IMPORTANCE_NONE
    )
    channel.lightColor = baseContext.getColor(R.color.primary)
    channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    service.createNotificationChannel(channel)
    return channelId
}
