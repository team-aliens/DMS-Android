package team.aliens.data.datasource.remote

import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput

interface RemoteNotificationDataSource {
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
