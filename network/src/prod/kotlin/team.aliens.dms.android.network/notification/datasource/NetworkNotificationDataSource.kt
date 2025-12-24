package team.aliens.dms.android.network.notification.datasource

import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest

abstract class NetworkNotificationDataSource {

    abstract suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest)

    abstract suspend fun cancelFcmDeviceTokenRegistration(request: CancelFcmDeviceTokenRegistrationRequest)

    abstract suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest)

    abstract suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest)

    abstract suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest)

    abstract suspend fun fetchNotificationTopicStatus(deviceToken: String): FetchNotificationTopicStatusResponse

    abstract suspend fun fetchNotifications(): FetchNotificationsResponse
}
