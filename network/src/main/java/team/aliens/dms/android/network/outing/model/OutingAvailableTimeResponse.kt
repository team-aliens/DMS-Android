package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName

data class OutingAvailableTimeResponse(
    @SerializedName("outing_available_time") val availableTimes: List<AvailableTimeResponse>,
) {
    data class AvailableTimeResponse(
        @SerializedName("outing_available_time_id") val outingAvailableTimeId: String,
        @SerializedName("outing_time") val startTime: String,
        @SerializedName("arrival_time") val endTime: String,
        @SerializedName("enabled") val available: Boolean,
        @SerializedName("day_of_week") val dayOfWeek: String,
    )
}
