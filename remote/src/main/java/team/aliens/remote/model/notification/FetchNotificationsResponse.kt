package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import java.util.UUID
import team.aliens.domain.model.notification.NotificationTopic

data class FetchNotificationsResponse(
    @SerializedName("notifications") val notifications: List<NotificationResponse>,
) {
    data class NotificationResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("topic") val topic: NotificationTopic,
        @SerializedName("link_identifier") val linkId: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("created_at") val createdAt: String,
    )
}
