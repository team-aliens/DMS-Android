package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.NoticeApi
import team.aliens.data.remote.datasource.declaration.RemoteNoticeDataSource
import team.aliens.data.remote.response.notice.NewNoticeBooleanResponse
import team.aliens.data.remote.response.notice.NoticeListResponse
import team.aliens.data.util.sendHttpRequest
import team.aliens.domain.enums.NoticeListSCType
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun checkNoticeNewBoolean(): NewNoticeBooleanResponse =
        sendHttpRequest(httpRequest = { noticeApi.checkNewNoticeBoolean() })

    override suspend fun fetchNoticeList(
        order: NoticeListSCType,
    ): NoticeListResponse = sendHttpRequest(httpRequest = { noticeApi.fetchNoticeList(order) })

    override suspend fun fetchNoticeDetail(
        noticeId: String,
    ) = sendHttpRequest(httpRequest = { noticeApi.fetchNoticeDetail(noticeId) })
}
