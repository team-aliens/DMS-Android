package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput
import team.aliens.remote.apiservice.NotificationApiService
import team.aliens.remote.model.notification.RegisterDeviceNotificationTokenRequest
import javax.inject.Inject

class RemoteNotificationDataSourceImpl @Inject constructor(
    private val notificationApiService: NotificationApiService,
) : RemoteNotificationDataSource {
    override suspend fun registerDeviceNotificationToken(
        input: RegisterDeviceNotificationTokenInput,
    ) {
        notificationApiService.registerDeviceNotificationToken(
            request = RegisterDeviceNotificationTokenRequest(
                deviceToken = input.deviceToken,
            ),
        )
    }

    override suspend fun subscribeNotificationTopic(
        input: SubscribeNotificationTopicInput,
    ) {
        notificationApiService.subscribeNotificationTopic(
            topic = input.topic.name,
        )
    }

    override suspend fun unsubscribeNotificationTopic(
        input: UnsubscribeNotificationTopicInput,
    ) {
        notificationApiService.unsubscribeNotificationTopic(
            topic = input.topic.name,
        )
    }
}
