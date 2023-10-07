package team.aliens.dms_android.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms_android.network.annotation.RequiresAccessToken
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
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET(FetchNotices)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchNotices(
        @Query(Order) order: String,
    ): FetchNoticesResponse

    @GET(FetchNoticeDetails)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchNoticeDetails(
        @Path(NoticeId) noticeId: UUID,
    ): FetchNoticeDetailsResponse
}
