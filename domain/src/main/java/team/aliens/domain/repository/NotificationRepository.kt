package team.aliens.domain.repository

import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput

interface NotificationRepository {
    suspend fun registerDeviceNotificationToken(
        input: RegisterDeviceNotificationTokenInput,
    )

    suspend fun subscribeNotificationTopic(
        input: SubscribeNotificationTopicInput,
    )

    suspend fun unsubscribeNotificationTopic(
        input: UnsubscribeNotificationTopicInput,
    )
}
