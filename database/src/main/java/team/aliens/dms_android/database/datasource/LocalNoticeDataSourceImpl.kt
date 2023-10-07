package team.aliens.dms_android.database.datasource

import team.aliens.data.datasource.local.LocalNoticeDataSource
import team.aliens.domain.model.notice.Notice
import team.aliens.dms_android.database.room.dao.NoticeDao
import team.aliens.dms_android.database.room.entity.toData
import team.aliens.dms_android.database.room.entity.toDomain
import java.util.*
import javax.inject.Inject

class LocalNoticeDataSourceImpl @Inject constructor(
    private val noticeDao: NoticeDao,
) : LocalNoticeDataSource {

    override fun fetchNotice(
        noticeId: UUID,
    ): Notice {
        return noticeDao.findOne(
            id = noticeId,
        ).toDomain()
    }

    override fun fetchNotices(): List<Notice> {
        return noticeDao.findAll().toDomain()
    }

    override fun saveNotice(
        notice: Notice,
    ) {
        noticeDao.saveOne(
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