package team.aliens.dms.android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notification.NotificationTopic
import team.aliens.dms.android.domain.model.notification.SubscribeNotificationTopicInput

data class SubscribeNotificationTopicRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("topic") val topic: NotificationTopic,
)

fun SubscribeNotificationTopicInput.toData(): SubscribeNotificationTopicRequest {
    return SubscribeNotificationTopicRequest(
        deviceToken = this.deviceToken,
        topic = this.topic,
    )
}
