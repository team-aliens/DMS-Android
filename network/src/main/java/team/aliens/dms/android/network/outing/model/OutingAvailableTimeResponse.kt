package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import java.util.UUID

data class OutingAvailableTimeResponse(
    @SerializedName("outing_available_time") val availableTimes: List<AvailableTimeResponse>,
) {
    data class AvailableTimeResponse(
        @SerializedName("outing_available_time_id") val id: UUID,
        @SerializedName("outing_time") val startTime: LocalDateTime,
        @SerializedName("arrival_time") val endTime: LocalDateTime,
        @SerializedName("enabled") val available: Boolean,
        @SerializedName("day_of_week") val dayOfWeek: DayOfWeek,
    )
}
