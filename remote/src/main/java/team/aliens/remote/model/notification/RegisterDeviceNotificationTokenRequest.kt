package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("device_id") val deviceId: UUID,
    @SerializedName("operating_system") val operatingSystem: String,
)
