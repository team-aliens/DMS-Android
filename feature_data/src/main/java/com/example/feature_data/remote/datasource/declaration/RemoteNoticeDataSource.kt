package com.example.feature_data.remote.datasource.declaration

import com.example.feature_data.remote.response.notice.NewNoticeBooleanResponse
import com.example.feature_data.remote.response.notice.NoticeDetailResponse
import com.example.feature_data.remote.response.notice.NoticeListResponse
import com.example.feature_domain.enums.NoticeListSCType
import java.util.UUID

interface RemoteNoticeDataSource {

    suspend fun checkNoticeNewBoolean(): NewNoticeBooleanResponse

    suspend fun fetchNoticeList(order: NoticeListSCType): NoticeListResponse

    suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailResponse
}
