package team.aliens.data.remote.response.schools

import com.google.gson.annotations.SerializedName

data class FindIdResponse(
    @SerializedName("email") val email: String
)
