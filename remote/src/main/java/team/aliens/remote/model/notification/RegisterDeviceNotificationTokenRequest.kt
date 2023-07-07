package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("operating_system") val operatingSystem: String,
)

fun RegisterDeviceNotificationTokenInput.toData(): RegisterDeviceNotificationTokenRequest {
    return RegisterDeviceNotificationTokenRequest(
        deviceToken = this.deviceToken,
        deviceId = this.deviceId,
        operatingSystem = this.operatingSystem,
    )
}
