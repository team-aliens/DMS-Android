package team.aliens.dms.android.data.notification.repository

import team.aliens.dms.android.shared.model.Notification
import team.aliens.dms.android.shared.model.NotificationTopic
import team.aliens.dms.android.shared.model.NotificationTopicGroup

abstract class NotificationRepository {

    // TODO core 이전 고민
    abstract suspend fun registerDeviceNotificationToken(deviceToken: String)

    // TODO core 이전 고민
    abstract suspend fun cancelDeviceTokenRegistration(deviceToken: String)

    // TODO device token 파라미터 고민
    abstract suspend fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    )

    // TODO device token 파라미터 고민
    abstract suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    )

    abstract suspend fun batchUpdateNotificationTopic(subscriptions: List<NotificationTopic.Subscription>)

    // TODO device token 파라미터 고민
    abstract suspend fun fetchNotificationStatus(deviceToken: String): NotificationTopicGroup.Status

    abstract suspend fun fetchNotifications(): List<Notification>
}
