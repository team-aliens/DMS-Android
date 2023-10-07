package team.aliens.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.network.annotation.RequiresAccessToken
import team.aliens.network.common.HttpPath.Notice.FetchNoticeDetails
import team.aliens.network.common.HttpPath.Notice.FetchNotices
import team.aliens.network.common.HttpPath.Notice.FetchWhetherNewNoticesExist
import team.aliens.network.common.HttpProperty.PathVariable.NoticeId
import team.aliens.network.common.HttpProperty.QueryString.Order
import team.aliens.network.model.notice.FetchNoticeDetailsResponse
import team.aliens.network.model.notice.FetchNoticesResponse
import team.aliens.network.model.notice.FetchWhetherNewNoticesExistResponse
import java.util.*

interface NoticeApiService {

    @GET(FetchWhetherNewNoticesExist)
    @RequiresAccessToken
    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET(FetchNotices)
    @RequiresAccessToken
    suspend fun fetchNotices(
        @Query(Order) order: String,
    ): FetchNoticesResponse

    @GET(FetchNoticeDetails)
    @RequiresAccessToken
    suspend fun fetchNoticeDetails(
        @Path(NoticeId) noticeId: UUID,
    ): FetchNoticeDetailsResponse
}
