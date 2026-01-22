package team.aliens.dms.android.data.notice.repository

import team.aliens.dms.android.data.notice.mapper.toModel
import team.aliens.dms.android.data.notice.model.LatestNotice
import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.network.notice.datasource.NetworkNoticeDataSource
import team.aliens.dms.android.shared.model.Order
import java.util.UUID
import javax.inject.Inject

internal class NoticeRepositoryImpl @Inject constructor(
    private val networkNoticeDataSource: NetworkNoticeDataSource,
) : NoticeRepository() {

    override suspend fun fetchWhetherNewNoticesExist(): Result<Boolean> =
        networkNoticeDataSource.fetchWhetherNewNoticesExist()
            .map { it.whetherNewNotices }

    override suspend fun fetchNoticeDetails(noticeId: UUID): Result<Notice> =
        networkNoticeDataSource.fetchNoticeDetails(noticeId)
            .map { it.toModel() }

    override suspend fun fetchNotices(order: Order): Result<List<Notice>> =
        networkNoticeDataSource.fetchNotices(order.name)
            .map { it.toModel() }

    override suspend fun fetchLatestNotice(): Result<LatestNotice> =
        networkNoticeDataSource.fetchLatestNotice()
            .map { it.toModel() }
}
