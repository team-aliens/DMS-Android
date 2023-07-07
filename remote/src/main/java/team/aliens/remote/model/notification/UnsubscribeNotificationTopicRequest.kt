package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.NotificationTopic

data class UnsubscribeNotificationTopicRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("topic") val topic: NotificationTopic,
)

fun UnsubscribeNotificationTopicRequest.toData(): UnsubscribeNotificationTopicRequest {
    return UnsubscribeNotificationTopicRequest(
        deviceToken = deviceToken,
        topic = topic,
    )
}
