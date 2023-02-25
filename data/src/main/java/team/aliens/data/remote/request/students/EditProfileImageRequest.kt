package team.aliens.data.remote.request.students

import com.google.gson.annotations.SerializedName

data class EditProfileImageRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)
