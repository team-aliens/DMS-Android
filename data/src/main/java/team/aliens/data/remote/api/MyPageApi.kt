package team.aliens.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import team.aliens.data.remote.response.mypage.FetchMyPageResponse
import team.aliens.data.remote.response.mypage.FetchPointListResponse
import team.aliens.data.remote.url.DmsUrl
import team.aliens.domain.enums.PointType

interface MyPageApi {

    @GET(DmsUrl.MyPage.MyPage)
    suspend fun fetchMyPage(): FetchMyPageResponse

    @GET(DmsUrl.MyPage.Point)
    suspend fun fetchPoint(
        @Query("type") pointType: PointType
    ): FetchPointListResponse
}
