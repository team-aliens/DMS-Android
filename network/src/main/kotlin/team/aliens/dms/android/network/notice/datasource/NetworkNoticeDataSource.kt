package team.aliens.dms.android.network.notice.datasource

import team.aliens.dms.android.network.notice.model.FetchLatestNoticeResponse
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import java.util.UUID

abstract class NetworkNoticeDataSource {

    abstract suspend fun fetchWhetherNewNoticesExist(): Result<FetchWhetherNewNoticesExistResponse>

    abstract suspend fun fetchNotices(order: String): Result<FetchNoticesResponse>

    abstract suspend fun fetchNoticeDetails(noticeId: UUID): Result<FetchNoticeDetailsResponse>

    abstract suspend fun fetchLatestNotice(): Result<FetchLatestNoticeResponse>
}
