package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)
