package com.example.feature_data.remote.api

import com.example.feature_data.remote.response.notice.NewNoticeBooleanResponse
import com.example.feature_data.remote.response.notice.NoticeDetailResponse
import com.example.feature_data.remote.response.notice.NoticeListResponse
import com.example.feature_data.remote.url.DmsUrl
import com.example.feature_domain.enums.NoticeListSCType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface NoticeApi {

    @GET(DmsUrl.Notice.NewNoticeBoolean)
    suspend fun checkNewNoticeBoolean(): NewNoticeBooleanResponse

    @GET(DmsUrl.notices)
    suspend fun fetchNoticeList(
        @Query("order") order: NoticeListSCType
    ): NoticeListResponse

    @GET(DmsUrl.Notice.NoticeDetail)
    suspend fun fetchNoticeDetail(
        @Path("notice-id") noticeId: UUID
    ): NoticeDetailResponse
}
