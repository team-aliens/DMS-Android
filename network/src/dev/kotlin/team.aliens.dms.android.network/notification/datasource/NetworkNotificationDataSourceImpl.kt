package team.aliens.dms.android.network.notification.datasource
import team.aliens.dms.android.network.notification.apiservice.NotificationApiService
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import java.util.UUID
import javax.inject.Inject

internal class NetworkNotificationDataSourceImpl @Inject constructor(
    private val notificationApiService: NotificationApiService,
) : NetworkNotificationDataSource() {
    override suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit> =
        runCatching { notificationApiService.registerFcmDeviceToken(request) }

    override suspend fun cancelFcmDeviceTokenRegistration(
        request: CancelFcmDeviceTokenRegistrationRequest,
    ): Result<Unit> =
        runCatching { notificationApiService.cancelFcmDeviceTokenRegistration(request) }

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit> =
        runCatching { notificationApiService.subscribeNotificationTopic(request) }

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit> =
        runCatching { notificationApiService.unsubscribeNotificationTopic(request) }

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit> =
        runCatching { notificationApiService.batchUpdateNotificationTopic(request) }

    override suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<FetchNotificationTopicStatusResponse> =
        runCatching { notificationApiService.fetchNotificationTopicStatus(deviceToken) }

    override suspend fun fetchNotifications(): Result<FetchNotificationsResponse> =
        runCatching { notificationApiService.fetchNotifications() }

    override suspend fun updateNotificationReadStatus(notification: UUID): Result<Unit>? =
        runCatching { notificationApiService.updateNotificationReadStatus(notification) }
}
