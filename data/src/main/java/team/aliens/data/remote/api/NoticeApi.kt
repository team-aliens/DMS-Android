package team.aliens.data.remote.api

import com.example.data.remote.response.notice.NoticeDetailResponse
import com.example.data.remote.response.notice.NoticeListResponse
import com.example.data.remote.url.DmsUrl
import com.example.domain.enums.NoticeListSCType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeApi {

    @GET(DmsUrl.Notice.NewNoticeBoolean)
    suspend fun checkNewNoticeBoolean(): Boolean

    @GET(DmsUrl.notices)
    suspend fun fetchNoticeList(
        @Query("order") order: NoticeListSCType
    ): NoticeListResponse

    @GET(DmsUrl.Notice.NoticeDetail)
    suspend fun fetchNoticeDetail(
        @Path("notice-id") noticeId: String
    ): NoticeDetailResponse
}
