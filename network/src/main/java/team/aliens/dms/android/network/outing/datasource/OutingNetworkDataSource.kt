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
    abstract fun fetchOutingAvailableTime(type: String): OutingAvailableTimeResponse
    abstract fun applyOuting(req: ApplyOutingRequest): ApplyOutingResponse
    abstract fun fetchOutingApplicationDetails(applicationId: UUID): FetchOutingApplicationDetailsResponse
    abstract fun fetchCurrentAppliedOutingApplication(): FetchCurrentAppliedOutingApplicationResponse
    abstract fun cancelOuting(applicationId: UUID): Response<Unit?>
    abstract fun fetchOutingTypes(keyword: String?): FetchOutingTypesResponse
}
