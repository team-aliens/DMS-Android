package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.notice.NoticeDetailResponse
import team.aliens.data.remote.response.notice.NoticeListResponse
import team.aliens.domain.enums.NoticeListSCType

interface RemoteNoticeDataSource {

    suspend fun checkNoticeNewBoolean(): Boolean

    suspend fun fetchNoticeList(order: NoticeListSCType): NoticeListResponse

    suspend fun fetchNoticeDetail(noticeId: String): NoticeDetailResponse
}
