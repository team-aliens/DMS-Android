package team.aliens.dms.android.app

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.notification.notificationPermissionGranted

private object Notifications {
    const val NOTIFICATION_ID = 0
    const val NOTIFICATION_CHANNEL_ID = "dms"
    const val CHANNEL_NAME = "dms"
    const val CHANNEL_DESCRIPTION = "dms notification channel"
}

class NotificationManager(
    private val context: Context,
) {

    init {
        createNotificationChannel()
    }

    private val notificationManagerCompat: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val messageId = System.currentTimeMillis().toInt()
    private val intent = Intent(context, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    private val pendingIntent = PendingIntent.getActivity(
        context, messageId, intent, PendingIntent.FLAG_IMMUTABLE
    )

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, Notifications.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(DmsIcon.Notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
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
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                Notifications.NOTIFICATION_CHANNEL_ID,
                Notifications.CHANNEL_NAME,
                importance
            ).apply {
                this.description = Notifications.CHANNEL_DESCRIPTION
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
