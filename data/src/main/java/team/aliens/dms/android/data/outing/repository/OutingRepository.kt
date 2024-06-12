package team.aliens.dms.android.data.outing.repository

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationId
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import java.util.UUID

abstract class OutingRepository {
    abstract suspend fun fetchOutingApplicationTimes(dayOfWeek: DayOfWeek): List<OutingApplicationTime>

    abstract suspend fun applyOuting(
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        type: String,
        reason: String?,
        companionIds: List<UUID>,
    ): OutingApplicationId

    abstract suspend fun fetchCurrentAppliedOutingApplication(): CurrentAppliedOutingApplication

    abstract suspend fun cancelOuting(applicationId: UUID)

    abstract suspend fun fetchOutingTypes(keyword: String?): List<String>
}
