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

    override suspend fun registerDeviceNotificationToken(deviceToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun cancelDeviceTokenRegistration(deviceToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ) = networkNotificationDataSource.subscribeNotificationTopic(
        request = SubscribeNotificationTopicRequest(
            deviceToken = deviceToken,
            topic = topic.name,
        )
    )

    override suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ) = networkNotificationDataSource.unsubscribeNotificationTopic(
        request = UnsubscribeNotificationTopicRequest(
            deviceToken = deviceToken,
            topic = topic.name,
        )
    )

    override suspend fun batchUpdateNotificationTopic(
        subscriptions: List<NotificationTopic.Subscription>,
    ) = networkNotificationDataSource.batchUpdateNotificationTopic(
        request = BatchUpdateNotificationTopicRequest(
            topics = subscriptions.toModel()
        )
    )

    override suspend fun fetchNotificationStatus(deviceToken: String): List<NotificationTopicGroup.Status> =
        networkNotificationDataSource.fetchNotificationTopicStatus(deviceToken = deviceToken)
            .toModel()

    override suspend fun fetchNotifications(): List<Notification> =
        networkNotificationDataSource.fetchNotifications().toModel()

    override suspend fun saveDeviceToken(deviceToken: String) {
        deviceDataStoreDataSource.storeDeviceToken(deviceToken)
    }

    override suspend fun getDeviceToken(): String =
        deviceDataStoreDataSource.loadDeviceToken()
}
