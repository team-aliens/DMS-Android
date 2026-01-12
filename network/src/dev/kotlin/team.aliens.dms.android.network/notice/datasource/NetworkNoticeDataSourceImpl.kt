package team.aliens.dms.android.network.notice.datasource
import team.aliens.dms.android.network.notice.apiservice.NoticeApiService
import team.aliens.dms.android.network.notice.model.FetchNoticeDetailsResponse
import team.aliens.dms.android.network.notice.model.FetchNoticesResponse
import team.aliens.dms.android.network.notice.model.FetchWhetherNewNoticesExistResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkNoticeDataSourceImpl @Inject constructor(
    private val noticeApiService: NoticeApiService,
) : NetworkNoticeDataSource() {
    override suspend fun fetchWhetherNewNoticesExist(): Result<FetchWhetherNewNoticesExistResponse> =
        runCatching { noticeApiService.fetchWhetherNewNoticesExist() }

    override suspend fun fetchNotices(order: String): Result<FetchNoticesResponse> =
        runCatching { noticeApiService.fetchNotices(order) }

    override suspend fun fetchNoticeDetails(noticeId: UUID): Result<FetchNoticeDetailsResponse> =
        runCatching { noticeApiService.fetchNoticeDetails(noticeId) }
}
