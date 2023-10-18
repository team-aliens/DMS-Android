package team.aliens.dms.android.domain._legacy.usecase.notification

import team.aliens.dms.android.domain.model.notification.Notification
import team.aliens.dms.android.domain.model.notification.toModel
import team.aliens.dms.android.domain.repository.NotificationRepository
import javax.inject.Inject

class FetchNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return kotlin.runCatching { notificationRepository.fetchNotifications().toModel() }
    }
}
