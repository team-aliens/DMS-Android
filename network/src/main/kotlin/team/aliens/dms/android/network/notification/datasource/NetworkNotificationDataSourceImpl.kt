package team.aliens.dms.android.network.notification.datasource
import team.aliens.dms.android.network.notification.apiservice.NotificationApiService
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import java.util.UUID
import javax.inject.Inject

internal class NetworkNotificationDataSourceImpl @Inject constructor(
    private val notificationApiService: NotificationApiService,
) : NetworkNotificationDataSource() {
    override suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit> =
        runCatchingCancellable { notificationApiService.registerFcmDeviceToken(request) }

    override suspend fun cancelFcmDeviceTokenRegistration(
        request: CancelFcmDeviceTokenRegistrationRequest,
    ): Result<Unit> =
        runCatchingCancellable { notificationApiService.cancelFcmDeviceTokenRegistration(request) }

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit> =
        runCatchingCancellable { notificationApiService.subscribeNotificationTopic(request) }

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit> =
        runCatchingCancellable { notificationApiService.unsubscribeNotificationTopic(request) }

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit> =
        runCatchingCancellable { notificationApiService.batchUpdateNotificationTopic(request) }

    override suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<FetchNotificationTopicStatusResponse> =
        runCatchingCancellable { notificationApiService.fetchNotificationTopicStatus(deviceToken) }

    override suspend fun fetchNotifications(): Result<FetchNotificationsResponse> =
        runCatchingCancellable { notificationApiService.fetchNotifications() }

    override suspend fun updateNotificationReadStatus(notification: UUID): Result<Unit> =
        runCatchingCancellable { notificationApiService.updateNotificationReadStatus(notification) }
}
