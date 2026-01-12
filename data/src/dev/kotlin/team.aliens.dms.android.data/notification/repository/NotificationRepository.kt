package team.aliens.dms.android.data.notification.repository

import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import java.util.UUID

abstract class NotificationRepository {

    // TODO core 이전 고민
    abstract suspend fun registerDeviceNotificationToken(deviceToken: String): Result<Unit>

    // TODO core 이전 고민
    abstract suspend fun cancelDeviceTokenRegistration(deviceToken: String): Result<Unit>

    // TODO device token 파라미터 고민
    abstract suspend fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit>

    // TODO device token 파라미터 고민
    abstract suspend fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ): Result<Unit>

    abstract suspend fun batchUpdateNotificationTopic(subscriptions: List<NotificationTopic.Subscription>): Result<Unit>

    // TODO device token 파라미터 고민
    abstract suspend fun fetchNotificationStatus(deviceToken: String): Result<List<NotificationTopicGroup.Status>>

    abstract suspend fun fetchNotifications(): Result<List<Notification>>

    abstract suspend fun saveDeviceToken(deviceToken: String): Result<Unit>

    abstract suspend fun getDeviceToken(): Result<String>

    abstract suspend fun updateNotificationReadStatus(notification: UUID): Result<Unit>?
}
