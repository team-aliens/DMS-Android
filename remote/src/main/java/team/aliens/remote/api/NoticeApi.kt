package team.aliens.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.remote.common.DormHttpPath.Notices.FetchNoticeDetails
import team.aliens.remote.common.DormHttpPath.Notices.FetchNotices
import team.aliens.remote.common.DormHttpPath.Notices.FetchWhetherNewNoticesExist
import team.aliens.remote.common.HttpProperty.PathVariable.NoticeId
import team.aliens.remote.common.HttpProperty.QueryString.Order
import team.aliens.remote.model.notice.FetchNoticeDetailsResponse
import team.aliens.remote.model.notice.FetchNoticesResponse
import team.aliens.remote.model.notice.FetchWhetherNewNoticesExistResponse
import java.util.*

interface NoticeApi {

    @GET(FetchWhetherNewNoticesExist)
    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET(FetchNoticeDetails)
    suspend fun fetchNoticeDetails(
        @Path(NoticeId) noticeId: UUID,
    ): FetchNoticeDetailsResponse

    @GET(FetchNotices)
    suspend fun fetchNotices(
        @Query(Order) order: String,
    ): FetchNoticesResponse
}
