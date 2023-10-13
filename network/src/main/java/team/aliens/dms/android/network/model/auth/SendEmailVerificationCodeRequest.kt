package team.aliens.dms.android.network.model.auth

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.auth.SendEmailVerificationCodeInput

data class SendEmailVerificationCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: String,
)

internal fun SendEmailVerificationCodeInput.toData(): SendEmailVerificationCodeRequest {
    return SendEmailVerificationCodeRequest(
        email = this.email,
        type = this.type.name,
    )
}
