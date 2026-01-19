package team.aliens.dms.android.data.notification.repository

import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.notification.model.toModel
import team.aliens.dms.android.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import java.util.UUID
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
    private val deviceDataStoreDataSource: DeviceDataStoreDataSource,
) : NotificationRepository() {

    override suspend fun registerDeviceNotificationToken(deviceToken: String): Result<Unit> =
        networkNotificationDataSource.registerFcmDeviceToken(
            request = RegisterFcmDeviceTokenRequest(deviceToken = deviceToken),
        )

    override suspend fun cancelDeviceTokenRegistration(deviceToken: String): Result<Unit> =
        networkNotificationDataSource.cancelFcmDeviceTokenRegistration(
            request = CancelFcmDeviceTokenRegistrationRequest(deviceToken = deviceToken),
        )

    override suspend fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit> =
        networkNotificationDataSource.subscribeNotificationTopic(
            request = SubscribeNotificationTopicRequest(
                deviceToken = deviceToken,
                topic = topic.name,
            ),
        )

    override suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit> =
        networkNotificationDataSource.unsubscribeNotificationTopic(
            request = UnsubscribeNotificationTopicRequest(
                deviceToken = deviceToken,
                topic = topic.name,
            ),
        )

    override suspend fun batchUpdateNotificationTopic(
        subscriptions: List<NotificationTopic.Subscription>,
    ): Result<Unit> =
        networkNotificationDataSource.batchUpdateNotificationTopic(
            request = BatchUpdateNotificationTopicRequest(
                topics = subscriptions.toModel(),
            ),
        )

    override suspend fun fetchNotificationStatus(
        deviceToken: String,
    ): Result<List<NotificationTopicGroup.Status>> =
        networkNotificationDataSource.fetchNotificationTopicStatus(deviceToken = deviceToken)
            .map { it.toModel() }

    override suspend fun fetchNotifications(): Result<List<Notification>> =
        networkNotificationDataSource.fetchNotifications().map { it.toModel() }

    override suspend fun saveDeviceToken(deviceToken: String): Result<Unit> = runCatching {
        deviceDataStoreDataSource.storeDeviceToken(deviceToken)
    }

    override suspend fun getDeviceToken(): Result<String> = runCatching {
        deviceDataStoreDataSource.loadDeviceToken()
    }

    override suspend fun updateNotificationReadStatus(notification: UUID): Result<Unit> =
        networkNotificationDataSource.updateNotificationReadStatus(notification)

}
