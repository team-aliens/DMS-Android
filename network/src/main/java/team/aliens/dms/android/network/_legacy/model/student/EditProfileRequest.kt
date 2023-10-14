package team.aliens.dms.android.network._legacy.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.student.EditProfileInput

data class EditProfileRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)

internal fun EditProfileInput.toData(): EditProfileRequest {
    return EditProfileRequest(
        profileImageUrl = this.profileImageUrl,
    )
}
