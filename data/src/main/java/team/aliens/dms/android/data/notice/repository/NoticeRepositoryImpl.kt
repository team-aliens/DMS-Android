package team.aliens.dms.android.data.notice.repository

import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.network.notice.datasource.NetworkNoticeDataSource
import team.aliens.dms.android.shared.model.Order
import java.util.UUID
import javax.inject.Inject

internal class NoticeRepositoryImpl @Inject constructor(
    private val networkNoticeDataSource: NetworkNoticeDataSource,
) : NoticeRepository() {

    override suspend fun fetchWhetherNewNoticesExist(): Boolean =
        networkNoticeDataSource.fetchWhetherNewNoticesExist().whetherNewNotices

    override suspend fun fetchNoticeDetails(noticeId: UUID): Notice {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotices(order: Order): List<Notice> {
        TODO("Not yet implemented")
    }
}
