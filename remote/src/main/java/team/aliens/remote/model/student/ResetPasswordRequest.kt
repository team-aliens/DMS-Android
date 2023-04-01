package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.student.ResetPasswordInput

data class ResetPasswordRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("name") val studentName: String,
    @SerializedName("email") val email: String,
    @SerializedName("auth_code") val emailVerificationCode: String,
    @SerializedName("new_password") val newPassword: String,
)

internal fun ResetPasswordInput.toData(): ResetPasswordRequest {
    return ResetPasswordRequest(
        accountId = this.accountId,
        studentName = this.studentName,
        email = this.email,
        emailVerificationCode = this.emailVerificationCode,
        newPassword = this.newPassword,
    )
}
