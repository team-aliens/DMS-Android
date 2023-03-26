package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName

data class FindIdResponse(
    @SerializedName("email") val email: String,
)
