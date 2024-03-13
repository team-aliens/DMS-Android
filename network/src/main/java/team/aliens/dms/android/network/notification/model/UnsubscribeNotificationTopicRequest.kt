package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName

data class UnsubscribeNotificationTopicRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("topic") val topic: String,
)
