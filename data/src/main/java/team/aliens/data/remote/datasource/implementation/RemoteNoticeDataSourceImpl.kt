package team.aliens.data.remote.datasource.implementation

import com.example.data.remote.api.NoticeApi
import com.example.data.remote.datasource.declaration.RemoteNoticeDataSource
import com.example.data.remote.response.notice.NoticeListResponse
import com.example.data.util.sendHttpRequest
import com.example.domain.enums.NoticeListSCType
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun checkNoticeNewBoolean() = sendHttpRequest(httpRequest = { noticeApi.checkNewNoticeBoolean() })

    override suspend fun fetchNoticeList(
        order: NoticeListSCType,
    ): NoticeListResponse = sendHttpRequest(httpRequest = { noticeApi.fetchNoticeList(order) })

    override suspend fun fetchNoticeDetail(
        noticeId: String,
    ) = sendHttpRequest(httpRequest = { noticeApi.fetchNoticeDetail(noticeId) })
}
