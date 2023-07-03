package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
)
