package team.aliens.local_database.datasource.implementation

import team.aliens.local_database.dao.NoticeDao
import team.aliens.local_database.datasource.declaration.LocalNoticeDataSource
import team.aliens.local_database.entity.notice.NoticeDetailRoomEntity
import team.aliens.local_database.entity.notice.NoticeListRoomEntity
import java.util.UUID
import javax.inject.Inject

class LocalNoticeDataSourceImpl @Inject constructor(
    private val noticeDao: NoticeDao
) : LocalNoticeDataSource {

    override suspend fun saveNoticeDetail(
        noticeId: UUID,
        noticeDetailRoomEntity: NoticeDetailRoomEntity
    ) {
        noticeDetailRoomEntity.noticeId = noticeId
        noticeDao.saveNoticeDetail(noticeDetailRoomEntity)
    }

    override suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailRoomEntity =
        noticeDao.fetchNoticeDetail(noticeId)


    override suspend fun saveNoticeList(noticeListRoomEntity: NoticeListRoomEntity) {
        noticeDao.saveNoticeList(noticeListRoomEntity)
    }

    override suspend fun fetchNoticeList(order: String): NoticeListRoomEntity =
        noticeDao.fetchNoticeList(order)
}