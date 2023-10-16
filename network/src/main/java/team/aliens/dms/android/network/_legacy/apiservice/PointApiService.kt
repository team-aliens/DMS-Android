package team.aliens.dms.android.network._legacy.apiservice

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.dms.android.network.annotation.RequiresAccessToken
import team.aliens.dms.android.network.common.HttpPath.Point.FetchPoints
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Page
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Size
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Type
import team.aliens.dms.android.network.model.point.FetchPointsResponse

interface PointApiService {

    @GET(FetchPoints)
    @RequiresAccessToken
    suspend fun fetchPoints(
        @Query(Type) type: String,
        @Query(Page) page: Long?,
        @Query(Size) size: Long?,
    ): FetchPointsResponse
}
