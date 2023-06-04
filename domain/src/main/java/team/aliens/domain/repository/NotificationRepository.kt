package team.aliens.domain.repository

import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput

interface NotificationRepository {
    suspend fun registerDeviceToken(
        input: RegisterDeviceNotificationTokenInput,
    )

    suspend fun subscribeTopic(
        input: SubscribeNotificationTopicInput,
    )

    suspend fun unsubscribeTopic(
        input: UnsubscribeNotificationTopicInput,
    )
}
