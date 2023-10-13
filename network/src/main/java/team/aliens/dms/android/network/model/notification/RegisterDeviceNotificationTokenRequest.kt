package team.aliens.dms.android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.notification.RegisterDeviceNotificationTokenInput

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
)

fun RegisterDeviceNotificationTokenInput.toData(): RegisterDeviceNotificationTokenRequest {
    return RegisterDeviceNotificationTokenRequest(
        deviceToken = this.deviceToken,
    )
}
