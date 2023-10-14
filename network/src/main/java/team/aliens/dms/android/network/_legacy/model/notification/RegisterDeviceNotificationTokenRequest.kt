package team.aliens.dms.android.network._legacy.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notification.RegisterDeviceNotificationTokenInput

data class RegisterDeviceNotificationTokenRequest(
    @SerializedName("device_token") val deviceToken: String,
)

fun RegisterDeviceNotificationTokenInput.toData(): RegisterDeviceNotificationTokenRequest {
    return RegisterDeviceNotificationTokenRequest(
        deviceToken = this.deviceToken,
    )
}
