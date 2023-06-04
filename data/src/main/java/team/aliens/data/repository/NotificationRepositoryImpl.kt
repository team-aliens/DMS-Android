package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput
import team.aliens.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteNotificationDataSource: RemoteNotificationDataSource,
) : NotificationRepository {
    override suspend fun registerDeviceToken(
        input: RegisterDeviceNotificationTokenInput,
    ) {
        remoteNotificationDataSource.registerDeviceNotificationToken(input)
    }

    override suspend fun subscribeTopic(
        input: SubscribeNotificationTopicInput,
    ) {
        remoteNotificationDataSource.subscribeNotificationTopic(input)
    }

    override suspend fun unsubscribeTopic(
        input: UnsubscribeNotificationTopicInput,
    ) {
        remoteNotificationDataSource.unsubscribeNotificationTopic(input)
    }
}
