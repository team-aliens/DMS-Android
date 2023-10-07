package team.aliens.dms_android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.FetchNotificationTopicStatusInput

data class FetchNotificationTopicStatusRequest(
    @SerializedName("device_token") val deviceToken: String,
)

fun FetchNotificationTopicStatusInput.toData(): FetchNotificationTopicStatusRequest {
    return FetchNotificationTopicStatusRequest(
        deviceToken = this.deviceToken,
    )
}
