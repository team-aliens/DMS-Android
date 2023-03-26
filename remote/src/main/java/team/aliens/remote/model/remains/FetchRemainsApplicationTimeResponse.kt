package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName

data class FetchRemainsApplicationTimeResponse(
    @SerializedName("start_day_of_week") val startDayOfWeek: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_day_of_week") val endDayOfWeek: String,
    @SerializedName("end_time") val endTime: String,
)
