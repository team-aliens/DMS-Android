package team.aliens.dms.android.data.notification.repository

import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.notification.model.toModel
import team.aliens.dms.android.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
    private val deviceDataStoreDataSource: DeviceDataStoreDataSource,
) : NotificationRepository() {

    override suspend fun registerDeviceNotificationToken(deviceToken: String): Result<Unit> = runCatching {
        TODO("Not yet implemented")
    }

    override suspend fun cancelDeviceTokenRegistration(deviceToken: String): Result<Unit> = runCatching {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit> = runCatching {
        networkNotificationDataSource.subscribeNotificationTopic(
            request = SubscribeNotificationTopicRequest(
                deviceToken = deviceToken,
                topic = topic.name,
            ),
        )
    }

    override suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit> = runCatching {
        networkNotificationDataSource.unsubscribeNotificationTopic(
            request = UnsubscribeNotificationTopicRequest(
                deviceToken = deviceToken,
                topic = topic.name,
            ),
        )
    }

    override suspend fun batchUpdateNotificationTopic(
        subscriptions: List<NotificationTopic.Subscription>,
    ): Result<Unit> = runCatching {
        networkNotificationDataSource.batchUpdateNotificationTopic(
            request = BatchUpdateNotificationTopicRequest(
                topics = subscriptions.toModel(),
            ),
        )
    }

    override suspend fun fetchNotificationStatus(deviceToken: String): Result<List<NotificationTopicGroup.Status>> = runCatching {
        networkNotificationDataSource.fetchNotificationTopicStatus(deviceToken = deviceToken)
            .toModel()
    }

    override suspend fun fetchNotifications(): Result<List<Notification>> = runCatching {
        networkNotificationDataSource.fetchNotifications().toModel()
    }

    override suspend fun saveDeviceToken(deviceToken: String): Result<Unit> = runCatching {
        deviceDataStoreDataSource.storeDeviceToken(deviceToken)
    }

    override suspend fun getDeviceToken(): Result<String> = runCatching {
        deviceDataStoreDataSource.loadDeviceToken()
    }
}
