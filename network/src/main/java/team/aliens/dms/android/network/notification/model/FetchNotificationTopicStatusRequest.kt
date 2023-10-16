package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName

data class FetchNotificationTopicStatusRequest(
    @SerializedName("device_token") val deviceToken: String,
)
