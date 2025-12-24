package team.aliens.dms.android.network.point.apiservice

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.point.model.FetchPointsResponse

internal interface PointApiService {

    @GET("/points")
    @RequiresAccessToken
    suspend fun fetchPoints(
        @Query("type") type: String,
        @Query("page") page: Long?,
        @Query("size") size: Long?,
    ): FetchPointsResponse
}
