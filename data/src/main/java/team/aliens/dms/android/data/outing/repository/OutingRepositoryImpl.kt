package team.aliens.dms.android.data.outing.repository

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationId
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import team.aliens.dms.android.data.outing.model.toModel
import team.aliens.dms.android.network.outing.datasource.OutingNetworkDataSource
import team.aliens.dms.android.network.outing.model.ApplyOutingRequest
import java.util.UUID
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val outingNetworkDataSource: OutingNetworkDataSource,
) : OutingRepository() {
    override suspend fun fetchOutingApplicationTimes(dayOfWeek: DayOfWeek): List<OutingApplicationTime> =
        outingNetworkDataSource.fetchOutingAvailableTime(dayOfWeek = dayOfWeek.name).toModel()

    override suspend fun applyOuting(
        date: LocalDate,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        type: String,
        reason: String?,
        companionIds: List<UUID>
    ): OutingApplicationId = outingNetworkDataSource.applyOuting(
        req = ApplyOutingRequest(
            date = date,
            startTime = startTime,
            endTime = endTime,
            type = type,
            reason = reason,
            companionIds = companionIds,
        ),
    ).applicationId

    override suspend fun fetchCurrentAppliedOutingApplication(): CurrentAppliedOutingApplication =
        outingNetworkDataSource.fetchCurrentAppliedOutingApplication().toModel()

    override suspend fun cancelOuting(applicationId: UUID) {
        outingNetworkDataSource.cancelOuting(applicationId = applicationId)
    }

    override suspend fun fetchOutingTypes(keyword: String): List<String> =
        outingNetworkDataSource.fetchOutingTypes(keyword = keyword).titles
}
