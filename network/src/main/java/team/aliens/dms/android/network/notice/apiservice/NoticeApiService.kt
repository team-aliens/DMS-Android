package team.aliens.dms.android.network.notice.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import team.aliens.dms.android.network.notice.model.Order
import java.util.UUID

internal abstract class NoticeApiService {

    @GET("/notices/status")
    @RequiresAccessToken
    abstract suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET("/notices/{notice-id}")
    @RequiresAccessToken
    abstract suspend fun fetchNoticeDetails(@Path("notice-id") noticeId: UUID): FetchNoticeDetailsResponse

    @GET("/notices")
    @RequiresAccessToken
    abstract suspend fun fetchNotices(@Query("order") order: Order): FetchNoticesResponse
}
