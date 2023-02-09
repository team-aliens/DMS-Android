package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteNoticeDataSource
import team.aliens.data.remote.response.notice.toEntity
import team.aliens.domain.entity.notice.NoticeDetailEntity
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType
import team.aliens.domain.repository.NoticeRepository
import team.aliens.local_database.datasource.declaration.LocalNoticeDataSource
import team.aliens.local_database.entity.notice.NoticeDetailRoomEntity
import team.aliens.local_database.entity.notice.NoticeListRoomEntity
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val remoteNoticeDataSource: RemoteNoticeDataSource,
    private val localNoticeDataSource: LocalNoticeDataSource,
) : NoticeRepository {

    override suspend fun newNoticeBoolean(): Boolean =
        remoteNoticeDataSource.checkNoticeNewBoolean()

    override suspend fun fetchNoticeList(
        order: NoticeListSCType,
    ): NoticeListEntity = remoteNoticeDataSource.fetchNoticeList(order).toEntity()

    override suspend fun fetchNoticeDetail(
        notice_id: String,
    ): NoticeDetailEntity = remoteNoticeDataSource.fetchNoticeDetail(notice_id).toEntity()
}

fun NoticeListEntity.NoticeValue.toDbEntity() = NoticeListRoomEntity.NoticeLocalValue(
    id = id,
    title = title,
    createAt = createAt,
)

fun NoticeListEntity.toDbEntity() = NoticeListRoomEntity(
    notices = notices.map { it.toDbEntity() },
)

fun NoticeDetailEntity.toDbEntity() = noticeId?.let {
    NoticeDetailRoomEntity(
        noticeId = it,
        title = title,
        content = content,
        createAt = createAt,
    )
}

fun NoticeListRoomEntity.NoticeLocalValue.toDmEntity() = NoticeListEntity.NoticeValue(
    id = id,
    title = title,
    createAt = createAt,
)

fun NoticeListRoomEntity.toDmEntity() = NoticeListEntity(notices = notices.map { it.toDmEntity() })
