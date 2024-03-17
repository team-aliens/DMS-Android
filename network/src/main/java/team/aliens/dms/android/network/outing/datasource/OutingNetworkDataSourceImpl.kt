package team.aliens.dms.android.network.outing.datasource

import retrofit2.Response
import team.aliens.dms.android.network.outing.apiservice.OutingApiService
import team.aliens.dms.android.network.outing.model.ApplyOutingRequest
import team.aliens.dms.android.network.outing.model.ApplyOutingResponse
import team.aliens.dms.android.network.outing.model.FetchCurrentAppliedOutingApplicationResponse
import team.aliens.dms.android.network.outing.model.FetchOutingApplicationDetailsResponse
import team.aliens.dms.android.network.outing.model.FetchOutingTypesResponse
import team.aliens.dms.android.network.outing.model.OutingAvailableTimeResponse
import java.util.UUID
import javax.inject.Inject

class OutingNetworkDataSourceImpl @Inject constructor(
    private val outingApiService: OutingApiService,
) : OutingNetworkDataSource() {
    override fun fetchOutingAvailableTime(dayOfWeek: String): OutingAvailableTimeResponse =
        outingApiService.fetchOutingAvailableTime(dayOfWeek)

    override fun applyOuting(req: ApplyOutingRequest): ApplyOutingResponse =
        outingApiService.applyOuting(req)

    override fun fetchOutingApplicationDetails(applicationId: UUID): FetchOutingApplicationDetailsResponse =
        outingApiService.fetchOutingApplicationDetails(applicationId)

    override fun fetchCurrentAppliedOutingApplication(): FetchCurrentAppliedOutingApplicationResponse =
        outingApiService.fetchCurrentAppliedOutingApplication()

    override fun cancelOuting(applicationId: UUID): Response<Unit?> =
        outingApiService.cancelOuting(applicationId)

    override fun fetchOutingTypes(keyword: String?): FetchOutingTypesResponse =
        outingApiService.fetchOutingTypes(keyword)
}
