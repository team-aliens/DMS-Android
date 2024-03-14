package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName

data class FetchCurrentAppliedOutingApplicationResponse(
    @SerializedName("out_at") val date: String,
    @SerializedName("outing_type") val type: String,
    @SerializedName("status") val status: String,
    @SerializedName("outing_time") val startTime: String,
    @SerializedName("arrival_time") val endTime: String,
    @SerializedName("reason") val reason: String?,
    @SerializedName("outing_companions") val followerNames: List<String>,
)
