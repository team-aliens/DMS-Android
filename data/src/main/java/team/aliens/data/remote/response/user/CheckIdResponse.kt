package team.aliens.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class CheckIdResponse(
    @SerializedName("email") val email: String,
)
