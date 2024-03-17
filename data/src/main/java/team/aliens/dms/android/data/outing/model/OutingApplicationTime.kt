package team.aliens.dms.android.data.outing.model

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.network.outing.model.OutingAvailableTimeResponse
import java.util.UUID

data class OutingApplicationTime(
    val id: UUID,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val available: Boolean,
    val dayOfWeek: DayOfWeek,
)

fun OutingAvailableTimeResponse.toModel(): List<OutingApplicationTime> =
    this.availableTimes.map(OutingAvailableTimeResponse.AvailableTimeResponse::toModel)

fun List<OutingAvailableTimeResponse.AvailableTimeResponse>.toModel(): List<OutingApplicationTime> =
    this.map(OutingAvailableTimeResponse.AvailableTimeResponse::toModel)

fun OutingAvailableTimeResponse.AvailableTimeResponse.toModel(): OutingApplicationTime =
    OutingApplicationTime(
        id = UUID.fromString(this.outingAvailableTimeId),
        startTime = this.startTime,
        endTime = this.endTime,
        available = this.available,
        dayOfWeek = DayOfWeek.valueOf(this.dayOfWeek),
    )
