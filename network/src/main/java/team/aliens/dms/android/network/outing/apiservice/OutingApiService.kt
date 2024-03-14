package team.aliens.dms.android.network.outing.apiservice

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.outing.model.ApplyOutingRequest
import team.aliens.dms.android.network.outing.model.ApplyOutingResponse
import team.aliens.dms.android.network.outing.model.FetchCurrentAppliedOutingApplicationResponse
import team.aliens.dms.android.network.outing.model.FetchOutingApplicationDetailsResponse
import team.aliens.dms.android.network.outing.model.FetchOutingTypesResponse
import team.aliens.dms.android.network.outing.model.OutingAvailableTimeResponse
import java.util.UUID

interface OutingApiService {

    @GET("/outings/available-time")
    fun fetchOutingAvailableTime(type: String): OutingAvailableTimeResponse

    @POST("/outings")
    fun applyOuting(req: ApplyOutingRequest): ApplyOutingResponse

    @POST("/outings/{outing-application-id}")
    fun fetchOutingApplicationDetails(
        @Path("outing-application-id") applicationId: UUID,
    ): FetchOutingApplicationDetailsResponse

    @GET("/outings/my")
    fun fetchCurrentAppliedOutingApplication(): FetchCurrentAppliedOutingApplicationResponse

    @DELETE("/outing/{outing-application-id}")
    fun cancelOuting(
        @Path("outing-application-id") applicationId: UUID,
    ): Response<Unit?>

    @GET("/outing/types")
    fun fetchOutingTypes(
        @Query("keyword") keyword: String?,
    ): FetchOutingTypesResponse
}
