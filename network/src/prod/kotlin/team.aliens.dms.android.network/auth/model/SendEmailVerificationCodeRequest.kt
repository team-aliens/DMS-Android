package team.aliens.dms.android.network.auth.model

import com.google.gson.annotations.SerializedName

data class SendEmailVerificationCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: String,
)
