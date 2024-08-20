package team.aliens.dms.android.database.notice.datasource

import team.aliens.dms.android.core.database.dao.NoticeDao
import team.aliens.dms.android.core.database.entity.NoticeEntity
import java.util.UUID
import javax.inject.Inject

class DatabaseNoticeDataSourceImpl @Inject constructor(
    private val noticeDao: NoticeDao,
) : DatabaseNoticeDataSource() {
    override fun queryNotice(id: UUID): NoticeEntity {
        return noticeDao.findById(id)
    }

    override fun queryAllNotices(): List<NoticeEntity> {
        return noticeDao.findAll()
    }

    override fun saveNotice(notice: NoticeEntity) {
        noticeDao.save(notice)
    }

    override fun saveNotices(notices: List<NoticeEntity>) {
        noticeDao.saveAll(notices)
    }
}
