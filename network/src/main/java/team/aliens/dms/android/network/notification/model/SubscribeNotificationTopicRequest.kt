package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName

data class SubscribeNotificationTopicRequest(
    @SerializedName("token") val deviceToken: String,
    @SerializedName("topic") val topic: String,
)
