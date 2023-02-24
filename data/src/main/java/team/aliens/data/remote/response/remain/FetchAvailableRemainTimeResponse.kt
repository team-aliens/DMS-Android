package team.aliens.data.remote.response.remain

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import java.time.DayOfWeek

data class FetchAvailableRemainTimeResponse(
    @SerializedName("start_day_of_week") val startDayOfWeek: DayOfWeek,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_day_of_week") val endDayOfWeek: DayOfWeek,
    @SerializedName("end_time") val endTime: String,
)

fun FetchAvailableRemainTimeResponse.toEntity(): AvailableRemainTimeEntity {
    return AvailableRemainTimeEntity(
        startDayOfWeek = this.startDayOfWeek,
        startTime = this.startTime,
        endDayOfWeek = this.endDayOfWeek,
        endTime = this.endTime,
    )
}
