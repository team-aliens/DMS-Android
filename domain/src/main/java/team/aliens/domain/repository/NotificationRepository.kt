package team.aliens.domain.repository

import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeTopicInput
import team.aliens.domain.model.notification.UnsubscribeTopicInput

interface NotificationRepository {
    suspend fun registerDeviceToken(
        input: RegisterDeviceNotificationTokenInput,
    )

    suspend fun subscribeTopic(
        input: SubscribeTopicInput,
    )

    suspend fun unsubscribeTopic(
        input: UnsubscribeTopicInput,
    )
}
