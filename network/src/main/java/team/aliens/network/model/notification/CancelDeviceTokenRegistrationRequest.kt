package team.aliens.network.model.notification

import com.google.gson.annotations.SerializedName
import java.util.UUID
import team.aliens.domain.model.notification.CancelDeviceTokenRegistrationInput

data class CancelDeviceTokenRegistrationRequest(
    @SerializedName("device_id") val deviceId: UUID,
)

fun CancelDeviceTokenRegistrationInput.toData(): CancelDeviceTokenRegistrationRequest {
    return CancelDeviceTokenRegistrationRequest(
        deviceId = deviceId,
    )
}
