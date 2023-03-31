package team.aliens.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Notices.FetchNoticeDetails
import team.aliens.remote.common.HttpPath.Notices.FetchNotices
import team.aliens.remote.common.HttpPath.Notices.FetchWhetherNewNoticesExist
import team.aliens.remote.common.HttpProperty.PathVariable.NoticeId
import team.aliens.remote.common.HttpProperty.QueryString.Order
import team.aliens.remote.model.notice.FetchNoticeDetailsResponse
import team.aliens.remote.model.notice.FetchNoticesResponse
import team.aliens.remote.model.notice.FetchWhetherNewNoticesExistResponse
import java.util.*

interface NoticeService {

    @GET(FetchWhetherNewNoticesExist)
    @RequiresAccessToken
    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET(FetchNoticeDetails)
    @RequiresAccessToken
    suspend fun fetchNoticeDetails(
        @Path(NoticeId) noticeId: UUID,
    ): FetchNoticeDetailsResponse

    @GET(FetchNotices)
    @RequiresAccessToken
    suspend fun fetchNotices(
        @Query(Order) order: String,
    ): FetchNoticesResponse
}
