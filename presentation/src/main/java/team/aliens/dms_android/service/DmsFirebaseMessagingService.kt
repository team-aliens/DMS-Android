package team.aliens.dms_android.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.usecase.notification.RegisterDeviceNotificationTokenUseCase
import team.aliens.presentation.R
import javax.inject.Inject

@AndroidEntryPoint
internal class DmsFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var registerDeviceNotificationTokenUseCase: RegisterDeviceNotificationTokenUseCase

    private val notificationChannelId by lazy {
        getString(R.string.notification_channel_id)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registerDeviceNotificationToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification -> sendNotification(notification) }
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        val notificationBuilder = NotificationCompat.Builder(
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
        ).setPriority(
            NotificationCompat.PRIORITY_DEFAULT,
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannelId,
                    getString(R.string.notification_channel_notice_title),
                    IMPORTANCE_DEFAULT,
                ),
            )
        }

        notificationManager.notify(getIdFromTime(), notificationBuilder.build())
    }

    private fun registerDeviceNotificationToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                registerDeviceNotificationTokenUseCase(
                    registerDeviceNotificationTokenInput = RegisterDeviceNotificationTokenInput(
                        token = token,
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
