package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.student.EditProfileInput

data class EditProfileRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)

internal fun EditProfileInput.toData(): EditProfileRequest {
    return EditProfileRequest(
        profileImageUrl = this.profileImageUrl,
    )
}
