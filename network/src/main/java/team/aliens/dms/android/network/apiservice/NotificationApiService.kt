package team.aliens.dms.android.network.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import team.aliens.dms.android.network.common.HttpPath
import team.aliens.dms.android.network.model.notification.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.model.notification.CancelDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.model.notification.FetchNotificationTopicStatusRequest
import team.aliens.dms.android.network.model.notification.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.model.notification.FetchNotificationsResponse
import team.aliens.dms.android.network.model.notification.RegisterDeviceNotificationTokenRequest
import team.aliens.dms.android.network.model.notification.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.model.notification.UnsubscribeNotificationTopicRequest

interface NotificationApiService {

    @POST(HttpPath.Notification.RegisterDeviceToken)
    suspend fun registerDeviceNotificationToken(
        @Body request: RegisterDeviceNotificationTokenRequest,
    )

    @DELETE(HttpPath.Notification.CancelDeviceTokenRegistration)
    suspend fun cancelDeviceTokenRegistration(
        @Body request: CancelDeviceTokenRegistrationRequest,
    )

    @POST(HttpPath.Notification.SubscribeTopic)
    suspend fun subscribeNotificationTopic(
        @Body request: SubscribeNotificationTopicRequest,
    )

    @DELETE(HttpPath.Notification.UnsubscribeTopic)
    suspend fun unsubscribeNotificationTopic(
        @Body request: UnsubscribeNotificationTopicRequest,
    )

    @PATCH(HttpPath.Notification.BatchUpdateTopic)
    suspend fun batchUpdateNotificationTopic(
        @Body request: BatchUpdateNotificationTopicRequest,
    )

    @GET(HttpPath.Notification.FetchNotificationTopicsStatus)
    suspend fun fetchNotificationTopicStatus(
        @Body request: FetchNotificationTopicStatusRequest,
    ): FetchNotificationTopicStatusResponse

    @GET(HttpPath.Notification.FetchNotifications)
    suspend fun fetchNotifications(): FetchNotificationsResponse
}
