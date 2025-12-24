package team.aliens.dms.android.network.student.model

import com.google.gson.annotations.SerializedName

data class FindIdResponse(
    @SerializedName("email") val email: String,
)
