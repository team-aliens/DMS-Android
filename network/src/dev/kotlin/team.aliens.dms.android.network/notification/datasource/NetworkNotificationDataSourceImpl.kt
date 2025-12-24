package team.aliens.dms.android.network.notification.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.notification.apiservice.NotificationApiService
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
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
        handleNetworkRequest { notificationApiService.registerFcmDeviceToken(request) }

    override suspend fun cancelFcmDeviceTokenRegistration(request: CancelFcmDeviceTokenRegistrationRequest) =
        handleNetworkRequest { notificationApiService.cancelFcmDeviceTokenRegistration(request) }

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest) =
        handleNetworkRequest { notificationApiService.subscribeNotificationTopic(request) }

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest) =
        handleNetworkRequest { notificationApiService.unsubscribeNotificationTopic(request) }

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest) =
        handleNetworkRequest { notificationApiService.batchUpdateNotificationTopic(request) }

    override suspend fun fetchNotificationTopicStatus(deviceToken: String): FetchNotificationTopicStatusResponse =
        handleNetworkRequest { notificationApiService.fetchNotificationTopicStatus(deviceToken) }

    override suspend fun fetchNotifications(): FetchNotificationsResponse =
        handleNetworkRequest { notificationApiService.fetchNotifications() }
}
