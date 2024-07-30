package team.aliens.dms.android.network.notification.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.network.notification.model.RegisterFcmDeviceTokenRequest
import team.aliens.dms.android.network.notification.model.SubscribeNotificationTopicRequest
import team.aliens.dms.android.network.notification.model.UnsubscribeNotificationTopicRequest

internal interface NotificationApiService {

    @POST("/notifications/tokens")
    suspend fun registerFcmDeviceToken(
        @Body request: RegisterFcmDeviceTokenRequest
    )

    @DELETE("/notifications/token")
    suspend fun cancelFcmDeviceTokenRegistration(
        @Body request: CancelFcmDeviceTokenRegistrationRequest
    )

    @POST("/notifications/topic")
    suspend fun subscribeNotificationTopic(
        @Body request: SubscribeNotificationTopicRequest
    )

    @DELETE("/notifications/topic")
    suspend fun unsubscribeNotificationTopic(
        @Body request: UnsubscribeNotificationTopicRequest
    )

    @PATCH("/notifications/topic")
    suspend fun batchUpdateNotificationTopic(
        @Body request: BatchUpdateNotificationTopicRequest
    )

    @GET("/notifications/topic")
    suspend fun fetchNotificationTopicStatus(
        @Query("device_token") deviceToken: String,
    ): FetchNotificationTopicStatusResponse

    @GET("/notifications")
    suspend fun fetchNotifications(): FetchNotificationsResponse
}
