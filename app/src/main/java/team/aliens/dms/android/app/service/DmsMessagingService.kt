package team.aliens.dms.android.app.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.notification.DeviceTokenManager
import team.aliens.dms.android.core.notification.NotificationManager
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class DmsMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    private val notificationManager: NotificationManager by lazy {
        NotificationManager(context = this)
    }
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: 디바이스 토큰 로컬에 저장 
        }
    }

    // TODO: 버전 대응
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.run {
            notificationManager.setNotificationContent(
                title = title,
                body = body,
            )
        }
        notificationManager.sendNotification()
    }
}
