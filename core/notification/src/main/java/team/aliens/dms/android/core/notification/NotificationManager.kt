package team.aliens.dms.android.core.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import team.aliens.dms.android.core.designsystem.DmsIcon

private object Notifications {
    const val NOTIFICATION_ID = 0
    const val NOTIFICATION_CHANNEL_ID = "dms"
    const val CHANNEL_NAME = "dms"
    const val CHANNEL_DESCRIPTION = "dms notification channel"
}

@RequiresApi(Build.VERSION_CODES.N)
class NotificationManager(
    private val context: Context,
) {

    init {
        createNotificationChannel()
    }

    private val notificationManagerCompat: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, Notifications.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(DmsIcon.Notification)
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }

    fun setNotificationContent(
        title: String?,
        body: String?,
    ) {
        notificationBuilder.run {
            title?.run { setContentTitle(this) }
            body?.run { setContentText(this) }
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {
        if (notificationPermissionGranted(context = context)) {
            notificationManagerCompat.notify(
                Notifications.NOTIFICATION_ID,
                notificationBuilder.build(),
            )
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                Notifications.NOTIFICATION_CHANNEL_ID,
                Notifications.CHANNEL_NAME,
                importance,
            ).apply {
                this.description = Notifications.CHANNEL_DESCRIPTION
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
