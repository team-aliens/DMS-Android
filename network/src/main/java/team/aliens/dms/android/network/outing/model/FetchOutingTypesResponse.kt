package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName

data class FetchOutingTypesResponse(
    @SerializedName("titles") val titles: List<String>,
)
