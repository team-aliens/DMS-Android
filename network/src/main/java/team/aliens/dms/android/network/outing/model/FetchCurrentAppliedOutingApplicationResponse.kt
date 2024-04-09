package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class FetchCurrentAppliedOutingApplicationResponse(
    @SerializedName("outing_date") val date: String,
    @SerializedName("outing_type_title") val type: String,
    @SerializedName("status") val status: String,
    @SerializedName("outing_time") val startTime: String,
    @SerializedName("arrival_time") val endTime: String,
    @SerializedName("reason") val reason: String?,
    @SerializedName("outing_companions") val companionNames: List<String>,
)
