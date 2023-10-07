package team.aliens.dms_android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
)

fun RegisterDeviceNotificationTokenInput.toData(): RegisterDeviceNotificationTokenRequest {
    return RegisterDeviceNotificationTokenRequest(
        deviceToken = this.deviceToken,
    )
}
