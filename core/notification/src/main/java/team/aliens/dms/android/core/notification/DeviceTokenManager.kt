package team.aliens.dms.android.core.notification

import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

class DeviceTokenManager @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(deviceToken: String) = runCatching {
        notificationRepository.registerDeviceNotificationToken(deviceToken = deviceToken)
    }
}
