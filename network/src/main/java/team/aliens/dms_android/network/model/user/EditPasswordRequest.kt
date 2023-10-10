package team.aliens.dms_android.network.model.user

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.user.EditPasswordInput

data class EditPasswordRequest(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String,
)

internal fun EditPasswordInput.toData(): EditPasswordRequest {
    return EditPasswordRequest(
        currentPassword = this.currentPassword,
        newPassword = this.newPassword,
    )
}
