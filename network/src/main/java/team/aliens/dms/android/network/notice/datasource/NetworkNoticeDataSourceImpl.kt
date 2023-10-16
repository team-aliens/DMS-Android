package team.aliens.dms.android.network.notice.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.notice.apiservice.NoticeApiService
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import team.aliens.dms.android.network.notice.model.Order
import java.util.UUID
import javax.inject.Inject

internal class NetworkNoticeDataSourceImpl @Inject constructor(
    private val noticeApiService: NoticeApiService,
) : NetworkNoticeDataSource() {
    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse =
        sendHttpRequest { noticeApiService.fetchWhetherNewNoticesExist() }

    override suspend fun fetchNotices(order: Order): FetchNoticesResponse =
        sendHttpRequest { noticeApiService.fetchNotices(order) }

    override suspend fun fetchNoticeDetails(noticeId: UUID): FetchNoticeDetailsResponse =
        sendHttpRequest { noticeApiService.fetchNoticeDetails(noticeId) }
}