package team.aliens.dms.android.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

class DeviceTokenManager @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend fun saveDeviceToken(deviceToken: String) {
        notificationRepository.saveDeviceToken(deviceToken = deviceToken)
    }

    suspend fun fetchDeviceToken() {
        val token = FirebaseMessaging.getInstance().token.await()
        withContext(Dispatchers.IO) {
            notificationRepository.saveDeviceToken(deviceToken = token)
        }
    }
}
