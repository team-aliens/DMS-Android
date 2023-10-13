package team.aliens.dms.android.network.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.notification.CancelDeviceTokenRegistrationInput
import java.util.UUID

data class CancelDeviceTokenRegistrationRequest(
    @SerializedName("device_id") val deviceId: UUID,
)

fun CancelDeviceTokenRegistrationInput.toData(): CancelDeviceTokenRegistrationRequest {
    return CancelDeviceTokenRegistrationRequest(
        deviceId = deviceId,
    )
}
