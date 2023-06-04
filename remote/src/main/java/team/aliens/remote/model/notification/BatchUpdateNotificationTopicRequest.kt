package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName

data class BatchUpdateNotificationTopicRequest(
    @SerializedName("topic_subscribes") val topics: List<NotificationTopicRequest>,
) {
    data class NotificationTopicRequest(
        @SerializedName("topic") val topic: String,
        @SerializedName("is_subscribe") val subscribe: Boolean,
    )
}
