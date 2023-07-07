package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class CancelDeviceTokenRegistrationRequest(
    @SerializedName("device_id") val deviceId: UUID, // todo discuss
    @SerializedName("operating_system") val operatingSystem: String = "ANDROID",
)
