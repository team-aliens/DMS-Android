package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notification.NotificationTopic

data class BatchUpdateNotificationTopicRequest(
    @SerializedName("topics_to_subscribe") val topics: List<NotificationTopicRequest>,
) {
    data class NotificationTopicRequest(
        @SerializedName("topic") val topic: NotificationTopic,
        @SerializedName("is_subscribed") val subscribed: Boolean,
    )
}
