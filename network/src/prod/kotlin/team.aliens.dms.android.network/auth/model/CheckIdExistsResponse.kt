package team.aliens.dms.android.network.auth.model

import com.google.gson.annotations.SerializedName

data class CheckIdExistsResponse(
    @SerializedName("email") val email: String,
)
