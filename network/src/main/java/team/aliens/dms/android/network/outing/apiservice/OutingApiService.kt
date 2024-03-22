package team.aliens.dms.android.network.outing.apiservice

import retrofit2.Response
import retrofit2.http.Body
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
    suspend fun fetchOutingAvailableTime(
        @Query("dayOfWeek") dayOfWeek: String,
    ): OutingAvailableTimeResponse

    @POST("/outings")
    suspend fun applyOuting(
        @Body req: ApplyOutingRequest,
    ): ApplyOutingResponse

    @POST("/outings/{outing-application-id}")
    suspend fun fetchOutingApplicationDetails(
        @Path("outing-application-id") applicationId: UUID,
    ): FetchOutingApplicationDetailsResponse

    @GET("/outings/my")
    suspend fun fetchCurrentAppliedOutingApplication(): FetchCurrentAppliedOutingApplicationResponse

    @DELETE("/outing/{outing-application-id}")
    suspend fun cancelOuting(
        @Path("outing-application-id") applicationId: UUID,
    ): Response<Unit?>

    @GET("/outing/types")
    suspend fun fetchOutingTypes(
        @Query("keyword") keyword: String?,
    ): FetchOutingTypesResponse
}
