package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.NotificationTopic
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput

data class UnsubscribeNotificationTopicRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("topic") val topic: NotificationTopic,
)

fun UnsubscribeNotificationTopicInput.toData(): UnsubscribeNotificationTopicRequest {
    return UnsubscribeNotificationTopicRequest(
        deviceToken = this.deviceToken,
        topic = this.topic,
    )
}