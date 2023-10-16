package team.aliens.dms.android.network.notice.datasource

import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import team.aliens.dms.android.network.notice.model.Order
import java.util.UUID

abstract class NetworkNoticeDataSource {

    abstract suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistResponse

    abstract suspend fun fetchNotices(order: Order): FetchNoticesResponse

    abstract suspend fun fetchNoticeDetails(noticeId: UUID): FetchNoticeDetailsResponse
}
