package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.NotificationTopic

data class BatchUpdateNotificationTopicRequest(
    @SerializedName("topics_to_subscribe") val topics: List<NotificationTopicRequest>,
) {
    data class NotificationTopicRequest(
        @SerializedName("topic") val topic: NotificationTopic,
        @SerializedName("is_subscribe") val subscribe: Boolean,
    )
}
