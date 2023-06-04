package team.aliens.remote.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import team.aliens.remote.common.HttpPath
import team.aliens.remote.common.HttpProperty.QueryString.Token
import team.aliens.remote.common.HttpProperty.QueryString.Topic
import team.aliens.remote.model.notification.BatchUpdateNotificationTopicRequest

interface NotificationApiService {

    @POST(HttpPath.Notification.RegisterDeviceToken)
    suspend fun registerDeviceNotificationToken(
        @Query(Token) token: String,
    )

    @POST(HttpPath.Notification.SubscribeTopic)
    suspend fun subscribeNotificationTopic(
        @Query(Topic) topic: String,
    )

    @DELETE(HttpPath.Notification.UnsubscribeTopic)
    suspend fun unsubscribeNotificationTopic(
        @Query(Topic) topic: String,
    )

    @PATCH(HttpPath.Notification.BatchUpdateTopic)
    suspend fun batchUpdateNotificationTopic(
        @Body request: BatchUpdateNotificationTopicRequest,
    )
}
