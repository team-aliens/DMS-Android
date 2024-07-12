package team.aliens.dms.android.network.auth.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("device_token") val deviceToken: String,
)
