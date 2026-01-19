package team.aliens.dms.android.data.notice.repository

import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.shared.model.Order
import java.util.UUID

abstract class NoticeRepository {

    abstract suspend fun fetchWhetherNewNoticesExist(): Result<Boolean>

    abstract suspend fun fetchNoticeDetails(noticeId: UUID): Result<Notice>

    abstract suspend fun fetchNotices(order: Order = Order.NEW): Result<List<Notice>>
}
