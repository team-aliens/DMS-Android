package team.aliens.dms.android.network.student.model

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("profile_image_url") val profileImageUrl: String,
)
