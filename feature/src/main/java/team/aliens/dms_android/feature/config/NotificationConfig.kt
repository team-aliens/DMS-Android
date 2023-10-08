package team.aliens.dms_android.feature.config

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import team.aliens.dms_android.presentation.R

// todo 내부 함수 분리 필요
@RequiresApi(Build.VERSION_CODES.O)
internal fun Application.initNotification() {
    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val groupId = getString(R.string.notification_group_notice_id)
    val groupName = getString(R.string.notification_group_notice_title)

    val notificationChannelGroup = NotificationChannelGroup(
        groupId,
        groupName,
    )

    val channelId = getString(R.string.notification_channel_id)
    val channelName = getString(R.string.notification_channel_notice_title)
    val channelDescription = getString(R.string.notification_channel_notice_description)
    val importance = NotificationManager.IMPORTANCE_DEFAULT

    val notificationChannel = NotificationChannel(
        channelId,
        channelName,
        importance,
    ).apply {
        description = channelDescription
        lightColor = getColor(R.color.primary)
        enableLights(true)
        group = groupId
    }

    notificationManager.run {
        createNotificationChannelGroup(notificationChannelGroup)
        createNotificationChannel(notificationChannel)
    }
}
