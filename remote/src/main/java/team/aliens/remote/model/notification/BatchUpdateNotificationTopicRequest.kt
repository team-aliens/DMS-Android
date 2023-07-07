package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.BatchUpdateNotificationTopicInput
import team.aliens.domain.model.notification.NotificationTopic

data class BatchUpdateNotificationTopicRequest(
    @SerializedName("topics_to_subscribe") val topics: List<NotificationTopicRequest>,
) {
    data class NotificationTopicRequest(
        @SerializedName("topic") val topic: NotificationTopic,
        @SerializedName("is_subscribed") val subscribed: Boolean,
    )
}

fun BatchUpdateNotificationTopicInput.NotificationTopicInformation.toData(): BatchUpdateNotificationTopicRequest.NotificationTopicRequest {
    return BatchUpdateNotificationTopicRequest.NotificationTopicRequest(
        topic = topic,
        subscribed = subscribed,
    )
}

fun List<BatchUpdateNotificationTopicInput.NotificationTopicInformation>.toData(): List<BatchUpdateNotificationTopicRequest.NotificationTopicRequest> {
    return this.map(BatchUpdateNotificationTopicInput.NotificationTopicInformation::toData)
}

fun BatchUpdateNotificationTopicInput.toData(): BatchUpdateNotificationTopicRequest {
    return BatchUpdateNotificationTopicRequest(
        topics = this.topics.toData(),
    )
}
