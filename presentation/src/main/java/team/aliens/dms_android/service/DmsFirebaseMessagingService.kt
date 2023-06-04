package team.aliens.dms_android.service

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registerDeviceNotificationToken(token)
    }

    @SuppressLint("MissingPermission") // todo need to be checked
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val channelId = getString(R.string.notification_channel_id)

        val builder =
            NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.ic_logo_image)
                .setContentTitle("FB Notification")
                .setContentText("FB Notification content. Time to use DMS!").setStyle(
                    NotificationCompat.BigPictureStyle().setSummaryText(
                        "FB Notification content. This is longer than the preview content. Thank you for using DMS!"
                    ),
                ).setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            val id = getIdFromTime()
            notify(id, builder.build())
        }
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
