package team.aliens.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.aliens.domain.enums.EmailType

data class GetEmailCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: EmailType,
)
