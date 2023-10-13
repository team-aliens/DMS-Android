package team.aliens.dms.android.database.datasource

import team.aliens.dms_android.core.database.dao.NoticeDao
import team.aliens.dms_android.data.datasource.local.LocalNoticeDataSource
import team.aliens.dms.android.database.room.entity.toData
import team.aliens.dms.android.database.room.entity.toDomain
import team.aliens.dms.android.domain.model.notice.Notice
import java.util.UUID
import javax.inject.Inject

class LocalNoticeDataSourceImpl @Inject constructor(
    private val noticeDao: NoticeDao,
) : LocalNoticeDataSource {

    override fun fetchNotice(
        noticeId: UUID,
    ): Notice {
        return noticeDao.findById(
            id = noticeId,
        ).toDomain()
    }

    override fun fetchNotices(): List<Notice> {
        return noticeDao.findAll().toDomain()
    }

    override fun saveNotice(
        notice: Notice,
    ) {
        noticeDao.save(
            noticeEntity = notice.toData(),
        )
    }

    override fun saveNotices(
        vararg notices: Notice,
    ) {
        noticeDao.saveAll(
            noticeEntities = arrayOf(*notices).toData(),
        )
    }
}
