package team.aliens.dms.android.network.notification.datasource

import retrofit2.http.PATCH
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import java.util.UUID

abstract class NetworkNotificationDataSource {

    abstract suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit>

    abstract suspend fun cancelFcmDeviceTokenRegistration(
        request: CancelFcmDeviceTokenRegistrationRequest,
    ): Result<Unit>

    abstract suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit>

    abstract suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit>

    abstract suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit>

    abstract suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<FetchNotificationTopicStatusResponse>

    abstract suspend fun fetchNotifications(): Result<FetchNotificationsResponse>

    abstract suspend fun updateNotificationReadStatus(notification: UUID): Result<Unit>
}
