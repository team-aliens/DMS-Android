package team.aliens.remote.model.auth

import com.google.gson.annotations.SerializedName

data class SendEmailVerificationCodeRequest(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
)
