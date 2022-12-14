package com.example.data.remote.datasource.declaration

import com.example.data.remote.response.notice.NewNoticeBooleanResponse
import com.example.data.remote.response.notice.NoticeDetailResponse
import com.example.data.remote.response.notice.NoticeListResponse
import com.example.domain.enums.NoticeListSCType
import java.util.UUID

interface RemoteNoticeDataSource {

    suspend fun checkNoticeNewBoolean(): Boolean

    suspend fun fetchNoticeList(order: NoticeListSCType): NoticeListResponse

    suspend fun fetchNoticeDetail(noticeId: String): NoticeDetailResponse
}
