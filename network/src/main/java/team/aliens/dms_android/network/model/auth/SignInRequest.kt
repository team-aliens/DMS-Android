package team.aliens.dms_android.network.model.auth

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.auth.SignInInput

data class SignInRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
)

internal fun SignInInput.toData(): SignInRequest {
    return SignInRequest(
        accountId = this.accountId,
        password = this.password,
    )
}