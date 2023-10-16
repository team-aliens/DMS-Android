package team.aliens.dms.android.database.notice.datasource

import team.aliens.dms.android.core.database.entity.NoticeEntity
import java.util.UUID

abstract class DatabaseNoticeDataSource {
    abstract fun queryNotice(id: UUID): NoticeEntity
    abstract fun queryAllNotices(): List<NoticeEntity>
    abstract fun saveNotice(notice: NoticeEntity)
    abstract fun saveNotices(notices: List<NoticeEntity>)
}
