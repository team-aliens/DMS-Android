package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName

data class FetchCurrentAppliedRemainsOptionResponse(
    @SerializedName("title") val title: String,
)
