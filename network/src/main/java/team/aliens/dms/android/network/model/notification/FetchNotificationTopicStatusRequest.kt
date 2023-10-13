package team.aliens.dms.android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notification.FetchNotificationTopicStatusInput

data class FetchNotificationTopicStatusRequest(
    @SerializedName("device_token") val deviceToken: String,
)

fun FetchNotificationTopicStatusInput.toData(): FetchNotificationTopicStatusRequest {
    return FetchNotificationTopicStatusRequest(
        deviceToken = this.deviceToken,
    )
}
