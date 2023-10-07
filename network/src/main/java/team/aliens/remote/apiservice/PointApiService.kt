package team.aliens.remote.apiservice

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Point.FetchPoints
import team.aliens.remote.common.HttpProperty.QueryString.Page
import team.aliens.remote.common.HttpProperty.QueryString.Size
import team.aliens.remote.common.HttpProperty.QueryString.Type
import team.aliens.remote.model.point.FetchPointsResponse

interface PointApiService {

    @GET(FetchPoints)
    @RequiresAccessToken
    suspend fun fetchPoints(
        @Query(Type) type: String,
        @Query(Page) page: Long?,
        @Query(Size) size: Long?,
    ): FetchPointsResponse
}