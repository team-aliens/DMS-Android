package team.aliens.dms.android.feature._legacy.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.MainActivity
import team.aliens.dms.android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms.android.domain.usecase.notification.RegisterDeviceNotificationTokenUseCase
import team.aliens.dms_android.feature.R
import javax.inject.Inject

@AndroidEntryPoint
internal class DmsFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var registerDeviceNotificationTokenUseCase: RegisterDeviceNotificationTokenUseCase

    private val notificationChannelId by lazy {
        getString(R.string.notification_channel_id)
    }
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registerDeviceNotificationToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification -> sendNotification(notification) }
    }

    // todo 권한 체크 구현
    @SuppressLint("MissingPermission")
    private fun sendNotification(notification: Notification) {
        val notificationBuilder = getFcmNotificationBuilder(notification)
        createNotificationChannel()

        with(NotificationManagerCompat.from(this)) {
            notify(getIdFromTime(), notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannelId,
                    getString(R.string.notification_channel_notice_title),
                    IMPORTANCE_DEFAULT,
                ),
            )
        }
    }

    private fun getFcmNotificationBuilder(notification: Notification): NotificationCompat.Builder {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(
            this,
            notificationChannelId,
        ).setSmallIcon(
            R.drawable.ic_logo_image_large,
        ).setContentTitle(
            notification.title,
        ).setContentText(
            notification.body,
        ).setStyle(
            NotificationCompat.BigTextStyle().bigText(notification.body),
        ).setContentIntent(
            pendingIntent,
        ).setPriority(
            NotificationCompat.PRIORITY_DEFAULT,
        ).setAutoCancel(
            true,
        )
    }

    private fun registerDeviceNotificationToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                registerDeviceNotificationTokenUseCase(
                    registerDeviceNotificationTokenInput = RegisterDeviceNotificationTokenInput(
                        deviceToken = token,
                    ),
                )
            }.onFailure {
                Toast.makeText(
                    this@DmsFirebaseMessagingService,
                    "failed to register device token, ${it.message}",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}

private fun getIdFromTime(): Int {
    return System.currentTimeMillis().hashCode()
}
