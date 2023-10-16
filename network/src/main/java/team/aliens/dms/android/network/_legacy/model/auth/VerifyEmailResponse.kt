package team.aliens.dms.android.network._legacy.model.auth

import com.google.gson.annotations.SerializedName

data class VerifyEmailResponse(
    @SerializedName("email") val email: String,
)
