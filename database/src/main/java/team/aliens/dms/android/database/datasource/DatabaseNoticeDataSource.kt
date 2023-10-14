package team.aliens.dms.android.database.datasource

import team.aliens.dms.android.domain.model.notice.Notice
import java.util.UUID

abstract class DatabaseNoticeDataSource {
    abstract fun queryNotice(id: UUID): Notice
    abstract fun queryAllNotices(): List<Notice>
    abstract fun saveNotice(notice: Notice)
    abstract fun saveNotices(notices: List<Notice>)
}
