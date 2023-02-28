package team.aliens.data.remote.request.user

import com.google.gson.annotations.SerializedName

data class EditPasswordRequest(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String,
)
