package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.NotificationTopic

data class SubscribeNotificationTopicRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("topic") val topic: NotificationTopic,
)
