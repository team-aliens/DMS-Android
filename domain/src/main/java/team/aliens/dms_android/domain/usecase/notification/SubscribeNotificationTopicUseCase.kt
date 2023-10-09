package team.aliens.dms_android.domain.usecase.notification

import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.repository.NotificationRepository
import javax.inject.Inject

class SubscribeNotificationTopicUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(
        subscribeNotificationTopicInput: SubscribeNotificationTopicInput,
    ) {
        notificationRepository.subscribeNotificationTopic(
            input = subscribeNotificationTopicInput,
        )
    }
}
