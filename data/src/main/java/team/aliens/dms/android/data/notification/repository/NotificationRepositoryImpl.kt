package team.aliens.dms.android.data.notification.repository

import team.aliens.dms.android.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.android.shared.model.Notification
import team.aliens.dms.android.shared.model.NotificationTopic
import team.aliens.dms.android.shared.model.NotificationTopicGroup
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
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
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun batchUpdateNotificationTopic(
        subscriptions: List<NotificationTopic.Subscription>,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotificationStatus(deviceToken: String): NotificationTopicGroup.Status {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotifications(): List<Notification> {
        TODO("Not yet implemented")
    }
}
