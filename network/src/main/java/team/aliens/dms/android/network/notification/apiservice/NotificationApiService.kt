package team.aliens.dms.android.network.notification.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest

internal abstract class NotificationApiService {

    @POST("/notifications/tokens")
    abstract suspend fun registerFcmDeviceToken(@Body request: RegisterFcmDeviceTokenRequest)

    @DELETE("/notifications/token")
    abstract suspend fun cancelFcmDeviceTokenRegistration(@Body request: CancelFcmDeviceTokenRegistrationRequest)

    @POST("/notifications/topic")
    abstract suspend fun subscribeNotificationTopic(@Body request: SubscribeNotificationTopicRequest)

    @DELETE("/notifications/topic")
    abstract suspend fun unsubscribeNotificationTopic(@Body request: UnsubscribeNotificationTopicRequest)

    @PATCH("/notifications/topic")
    abstract suspend fun batchUpdateNotificationTopic(@Body request: BatchUpdateNotificationTopicRequest)

    @GET("/notifications/topic")
    abstract suspend fun fetchNotificationTopicStatus(@Body request: FetchNotificationTopicStatusRequest): FetchNotificationTopicStatusResponse

    @GET("/notifications")
    abstract suspend fun fetchNotifications(): FetchNotificationsResponse
}
