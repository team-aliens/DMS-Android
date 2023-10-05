package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.domain.model.notification.BatchUpdateNotificationTopicInput
import team.aliens.domain.model.notification.CancelDeviceTokenRegistrationInput
import team.aliens.domain.model.notification.FetchNotificationTopicStatusInput
import team.aliens.domain.model.notification.FetchNotificationTopicStatusOutput
import team.aliens.domain.model.notification.FetchNotificationsOutput
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput
import team.aliens.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteNotificationDataSource: RemoteNotificationDataSource,
) : NotificationRepository {
    override suspend fun registerDeviceNotificationToken(
        input: RegisterDeviceNotificationTokenInput,
    ) {
        remoteNotificationDataSource.registerDeviceNotificationToken(input)
    }

    override suspend fun cancelDeviceTokenRegistration(
        input: CancelDeviceTokenRegistrationInput,
    ) {
        remoteNotificationDataSource.cancelDeviceTokenRegistration(input)
    }

    override suspend fun subscribeNotificationTopic(
        input: SubscribeNotificationTopicInput,
    ) {
        remoteNotificationDataSource.subscribeNotificationTopic(input)
    }

    override suspend fun unsubscribeNotificationTopic(
        input: UnsubscribeNotificationTopicInput,
    ) {
        remoteNotificationDataSource.unsubscribeNotificationTopic(input)
    }

    override suspend fun batchUpdateNotificationTopic(
        input: BatchUpdateNotificationTopicInput,
    ) {
        remoteNotificationDataSource.batchUpdateNotificationTopic(input)
    }

    override suspend fun fetchNotificationTopicStatus(
        input: FetchNotificationTopicStatusInput,
    ): FetchNotificationTopicStatusOutput {
        return remoteNotificationDataSource.fetchNotificationTopicStatus(input)
    }

    override suspend fun fetchNotifications(): FetchNotificationsOutput {
        return remoteNotificationDataSource.fetchNotifications()
    }
}