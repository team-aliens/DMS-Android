package team.aliens.dms.android.network.notification.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.notification.apiservice.NotificationApiService
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest
import javax.inject.Inject

internal class NetworkNotificationDataSourceImpl @Inject constructor(
    private val notificationApiService: NotificationApiService,
) : NetworkNotificationDataSource() {
    override suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest) =
        sendHttpRequest { notificationApiService.registerFcmDeviceToken(request) }

    override suspend fun cancelFcmDeviceTokenRegistration(request: CancelFcmDeviceTokenRegistrationRequest) =
        sendHttpRequest { notificationApiService.cancelFcmDeviceTokenRegistration(request) }

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest) =
        sendHttpRequest { notificationApiService.subscribeNotificationTopic(request) }

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest) =
        sendHttpRequest { notificationApiService.unsubscribeNotificationTopic(request) }

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest) =
        sendHttpRequest { notificationApiService.batchUpdateNotificationTopic(request) }

    override suspend fun fetchNotificationTopicStatus(request: FetchNotificationTopicStatusRequest): FetchNotificationTopicStatusResponse =
        sendHttpRequest { notificationApiService.fetchNotificationTopicStatus(request) }

    override suspend fun fetchNotifications(): FetchNotificationsResponse =
        sendHttpRequest { notificationApiService.fetchNotifications() }
}
