package team.aliens.dms.android.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

class DeviceTokenManager @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend fun saveDeviceToken(deviceToken: String) {
        notificationRepository.saveDeviceToken(deviceToken = deviceToken)
    }

    suspend fun fetchDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                // TODO: Handle error
            }
            CoroutineScope(Dispatchers.IO).launch {
                notificationRepository.saveDeviceToken(deviceToken = task.result)
            }
        }
    }
}
