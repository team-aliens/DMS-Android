package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import java.time.DayOfWeek

data class FetchRemainsApplicationTimeResponse(
    @SerializedName("start_day_of_week") val startDayOfWeek: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_day_of_week") val endDayOfWeek: String,
    @SerializedName("end_time") val endTime: String,
)

internal fun FetchRemainsApplicationTimeResponse.toDomain(): FetchRemainsApplicationTimeOutput {
    return FetchRemainsApplicationTimeOutput(
        startDayOfWeek = DayOfWeek.valueOf(this.startDayOfWeek),
        startTime = this.startTime,
        endDayOfWeek = DayOfWeek.valueOf(this.endDayOfWeek),
        endTime = this.endTime,
    )
}
