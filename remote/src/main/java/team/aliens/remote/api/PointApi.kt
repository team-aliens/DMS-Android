package team.aliens.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.remote.common.DormHttpPath.Points.FetchPoints
import team.aliens.remote.common.HttpProperty.QueryString.Page
import team.aliens.remote.common.HttpProperty.QueryString.Size
import team.aliens.remote.common.HttpProperty.QueryString.Type
import team.aliens.remote.model.point.FetchPointsResponse

interface PointApi {

    @GET(FetchPoints)
    suspend fun fetchPoints(
        @Query(Type) type: String,
        @Query(Page) page: String?,
        @Query(Size) size: String?,
    ): FetchPointsResponse
}
