package team.aliens.dms.android.network.outing.datasource

import retrofit2.Response
import team.aliens.dms.android.network.outing.model.ApplyOutingRequest
import team.aliens.dms.android.network.outing.model.ApplyOutingResponse
import team.aliens.dms.android.network.outing.model.FetchCurrentAppliedOutingApplicationResponse
import team.aliens.dms.android.network.outing.model.FetchOutingApplicationDetailsResponse
import team.aliens.dms.android.network.outing.model.FetchOutingTypesResponse
import team.aliens.dms.android.network.outing.model.OutingAvailableTimeResponse
import java.util.UUID

abstract class OutingNetworkDataSource {
    abstract suspend fun fetchOutingAvailableTime(dayOfWeek: String): OutingAvailableTimeResponse
    abstract suspend fun applyOuting(req: ApplyOutingRequest): ApplyOutingResponse
    abstract suspend fun fetchOutingApplicationDetails(applicationId: UUID): FetchOutingApplicationDetailsResponse
    abstract suspend fun fetchCurrentAppliedOutingApplication(): FetchCurrentAppliedOutingApplicationResponse
    abstract suspend fun cancelOuting(applicationId: UUID): Response<Unit?>
    abstract suspend fun fetchOutingTypes(keyword: String?): FetchOutingTypesResponse
}
