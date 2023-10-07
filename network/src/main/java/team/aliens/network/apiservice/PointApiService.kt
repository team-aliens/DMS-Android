package team.aliens.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.network.annotation.RequiresAccessToken
import team.aliens.network.common.HttpPath.Point.FetchPoints
import team.aliens.network.common.HttpProperty.QueryString.Page
import team.aliens.network.common.HttpProperty.QueryString.Size
import team.aliens.network.common.HttpProperty.QueryString.Type
import team.aliens.network.model.point.FetchPointsResponse

interface PointApiService {

    @GET(FetchPoints)
    @RequiresAccessToken
    suspend fun fetchPoints(
        @Query(Type) type: String,
        @Query(Page) page: Long?,
        @Query(Size) size: Long?,
    ): FetchPointsResponse
}
