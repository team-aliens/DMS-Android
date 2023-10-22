package team.aliens.dms.android.network.notice.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import java.util.UUID

internal interface NoticeApiService {

    @GET("/notices/status")
    @RequiresAccessToken
    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    @GET("/notices/{notice-id}")
    @RequiresAccessToken
    suspend fun fetchNoticeDetails(@Path("notice-id") noticeId: UUID): FetchNoticeDetailsResponse

    @GET("/notices")
    @RequiresAccessToken
    suspend fun fetchNotices(@Query("order") order: String): FetchNoticesResponse
}
