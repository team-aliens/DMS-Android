package team.aliens.remote.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Notice.FetchNoticeDetails
import team.aliens.remote.common.HttpPath.Notice.FetchNotices
import team.aliens.remote.common.HttpPath.Notice.FetchWhetherNewNoticesExist
import team.aliens.remote.common.HttpProperty.PathVariable.NoticeId
import team.aliens.remote.common.HttpProperty.QueryString.Order
import team.aliens.remote.model.notice.FetchNoticeDetailsResponse
import team.aliens.remote.model.notice.FetchNoticesResponse
import team.aliens.remote.model.notice.FetchWhetherNewNoticesExistResponse
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
