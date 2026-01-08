package team.aliens.dms.android.data.notice.repository

import team.aliens.dms.android.data.notice.model.Notice
import team.aliens.dms.android.shared.model.Order
import java.util.UUID

abstract class NoticeRepository {

    abstract suspend fun fetchWhetherNewNoticesExist(): Boolean

    abstract suspend fun fetchNoticeDetails(noticeId: UUID): Notice

    abstract suspend fun fetchNotices(order: Order = Order.NEW): List<Notice>
}
