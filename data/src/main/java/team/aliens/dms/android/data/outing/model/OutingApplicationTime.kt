package team.aliens.dms.android.data.outing.model

import org.threeten.bp.DayOfWeek
import team.aliens.dms.android.network.outing.model.OutingAvailableTimeResponse
import java.util.UUID

data class OutingApplicationTime(
    val id: UUID,
    val startTime: String,
    val endTime: String,
    val available: Boolean,
    val dayOfWeek: DayOfWeek,
)

fun OutingAvailableTimeResponse.toModel(): List<OutingApplicationTime> =
    this.availableTimes.toModel()

fun List<OutingAvailableTimeResponse.AvailableTimeResponse>.toModel(): List<OutingApplicationTime> =
    this.map(OutingAvailableTimeResponse.AvailableTimeResponse::toModel)

fun OutingAvailableTimeResponse.AvailableTimeResponse.toModel(): OutingApplicationTime =
    OutingApplicationTime(
        id = UUID.fromString(this.id),
        startTime = this.startTime,
        endTime = this.endTime,
        available = this.available,
        dayOfWeek = DayOfWeek.valueOf(this.dayOfWeek),
    )
