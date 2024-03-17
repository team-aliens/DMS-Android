package team.aliens.dms.android.data.outing.repository

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationId
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import java.util.UUID

abstract class OutingRepository {
    abstract fun fetchOutingApplicationTimes(dayOfWeek: DayOfWeek): List<OutingApplicationTime>

    abstract fun applyOuting(
        date: LocalDate,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        type: String,
        reason: String?,
        companionIds: List<UUID>,
    ): OutingApplicationId

    abstract fun fetchCurrentAppliedOutingApplication(): CurrentAppliedOutingApplication

    abstract fun cancelOuting(applicationId: UUID)

    abstract fun fetchOutingTypes(keyword: String): List<String>
}
