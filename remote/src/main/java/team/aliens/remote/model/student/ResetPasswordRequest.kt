package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("name") val studentName: String,
    @SerializedName("email") val email: String,
    @SerializedName("auth_code") val emailVerificationCode: String,
    @SerializedName("new_password") val newPassword: String,
)
