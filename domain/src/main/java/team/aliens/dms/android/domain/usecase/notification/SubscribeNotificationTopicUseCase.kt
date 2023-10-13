package team.aliens.dms.android.domain.usecase.notification

import team.aliens.dms.android.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.dms.android.domain.repository.NotificationRepository
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
