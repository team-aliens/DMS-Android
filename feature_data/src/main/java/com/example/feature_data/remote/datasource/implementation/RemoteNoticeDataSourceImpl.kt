package com.example.feature_data.remote.datasource.implementation

import com.example.feature_data.remote.api.NoticeApi
import com.example.feature_data.remote.datasource.declaration.RemoteNoticeDataSource
import com.example.feature_data.remote.response.notice.NewNoticeBooleanResponse
import com.example.feature_data.remote.response.notice.NoticeDetailResponse
import com.example.feature_data.remote.response.notice.NoticeListResponse
import com.example.feature_data.util.HttpHandler
import com.example.feature_domain.enums.NoticeListSCType
import java.util.UUID
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun checkNoticeNewBoolean() =
        HttpHandler<NewNoticeBooleanResponse>()
            .httpRequest { noticeApi.checkNewNoticeBoolean() }
            .sendRequest()

    override suspend fun fetchNoticeList(
        order: NoticeListSCType,
    ) = HttpHandler<NoticeListResponse>()
            .httpRequest { noticeApi.fetchNoticeList(order) }
            .sendRequest()

    override suspend fun fetchNoticeDetail(
        noticeId: UUID,
    ) = HttpHandler<NoticeDetailResponse>()
        .httpRequest { noticeApi.fetchNoticeDetail(noticeId) }
        .sendRequest()
}
