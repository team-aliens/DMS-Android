package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeTopicInput
import team.aliens.domain.model.notification.UnsubscribeTopicInput
import team.aliens.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteNotificationDataSource: RemoteNotificationDataSource,
) : NotificationRepository {
    override suspend fun registerDeviceToken(
        input: RegisterDeviceNotificationTokenInput,
    ) {
        remoteNotificationDataSource.registerDeviceToken(input)
    }

    override suspend fun subscribeTopic(
        input: SubscribeTopicInput,
    ) {
        remoteNotificationDataSource.subscribeTopic(input)
    }

    override suspend fun unsubscribeTopic(
        input: UnsubscribeTopicInput,
    ) {
        remoteNotificationDataSource.unsubscribeTopic(input)
    }
}
