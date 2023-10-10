package team.aliens.dms_android.domain.usecase.notification

import team.aliens.dms_android.domain.model.notification.UnsubscribeNotificationTopicInput
import team.aliens.dms_android.domain.repository.NotificationRepository
import javax.inject.Inject

class UnsubscribeNotificationTopicUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(
        unSubscribeNotificationTopicInput: UnsubscribeNotificationTopicInput,
    ) {
        notificationRepository.unsubscribeNotificationTopic(
            input = unSubscribeNotificationTopicInput,
        )
    }
}
