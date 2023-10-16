package team.aliens.dms.android.network.remains.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.DayOfWeek

data class FetchRemainsApplicationTimeResponse(
    @SerializedName("start_day_of_week") val startDayOfWeek: DayOfWeek,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_day_of_week") val endDayOfWeek: DayOfWeek,
    @SerializedName("end_time") val endTime: String,
)
