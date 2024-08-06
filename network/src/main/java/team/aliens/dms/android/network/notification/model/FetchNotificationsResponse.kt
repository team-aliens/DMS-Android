package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.util.UUID

data class FetchNotificationsResponse(
    @SerializedName("notifications") val notifications: List<NotificationResponse>,
) {
    data class NotificationResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("topic") val topic: String,
        @SerializedName("link_identifier") val linkId: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("is_read") val isRead: Boolean,
    )
}
