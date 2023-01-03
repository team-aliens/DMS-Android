package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteNoticeDataSource
import com.example.data.remote.response.notice.toEntity
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.notice.NewNoticeBooleanEntity
import com.example.domain.entity.notice.NoticeDetailEntity
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
import com.example.domain.repository.NoticeRepository
import com.example.local_database.datasource.declaration.LocalNoticeDataSource
import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val remoteNoticeDataSource: RemoteNoticeDataSource,
    private val localNoticeDataSource: LocalNoticeDataSource,
) : NoticeRepository {

    override suspend fun newNoticeBoolean(): Boolean =
        remoteNoticeDataSource.checkNoticeNewBoolean()

    override suspend fun fetchNoticeList(
        order: NoticeListSCType
    ): NoticeListEntity = remoteNoticeDataSource.fetchNoticeList(order).toEntity()

    override suspend fun fetchNoticeDetail(
        notice_id: String
    ): NoticeDetailEntity = remoteNoticeDataSource.fetchNoticeDetail(notice_id).toEntity()
}

fun NoticeListEntity.NoticeValue.toDbEntity() =
    NoticeListRoomEntity.NoticeLocalValue(
        id = id,
        title = title,
        createAt = createAt,
    )

fun NoticeListEntity.toDbEntity() =
    NoticeListRoomEntity(
        notices = notices.map { it.toDbEntity() },
    )

fun NoticeDetailEntity.toDbEntity() =
    noticeId?.let {
        NoticeDetailRoomEntity(
            noticeId = it,
            title = title,
            content = content,
            createAt = createAt,
        )
    }

fun NoticeListRoomEntity.NoticeLocalValue.toDmEntity() =
    NoticeListEntity.NoticeValue(
        id = id,
        title = title,
        createAt = createAt,
    )

fun NoticeListRoomEntity.toDmEntity() =
    NoticeListEntity(
        notices = notices.map { it.toDmEntity() }
    )
