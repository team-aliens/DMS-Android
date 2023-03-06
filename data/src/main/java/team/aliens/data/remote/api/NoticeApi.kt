package team.aliens.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.data.remote.response.notice.NewNoticeBooleanResponse
import team.aliens.data.remote.response.notice.NoticeDetailResponse
import team.aliens.data.remote.response.notice.NoticeListResponse
import team.aliens.data.remote.url.DmsUrl
import team.aliens.domain.enums.NoticeListSCType

interface NoticeApi {

    @GET(DmsUrl.Notice.NewNoticeBoolean)
    suspend fun checkNewNoticeBoolean(): NewNoticeBooleanResponse

    @GET(DmsUrl.notices)
    suspend fun fetchNoticeList(
        @Query("order") order: NoticeListSCType,
    ): NoticeListResponse

    @GET(DmsUrl.Notice.NoticeDetail)
    suspend fun fetchNoticeDetail(
        @Path("notice-id") noticeId: String,
    ): NoticeDetailResponse
}
