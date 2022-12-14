package com.example.local_database.entity.notice

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.notice.NoticeListLocalEntity
import java.util.UUID

@Entity(tableName = TableName.Notice.NOTICE_LIST)
data class NoticeListRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val notices: List<NoticeLocalValue>,
) {
    data class NoticeLocalValue(
        val id: UUID,
        val title: String,
        val createAt: String,
    )
}

fun NoticeListRoomEntity.NoticeLocalValue.toEntity() =
    NoticeListLocalEntity.NoticeValue(
        id = id,
        title = title,
        createAt = createAt,
    )

fun NoticeListRoomEntity.toEntity() =
    NoticeListLocalEntity(
        noticeValue = notices.map { it.toEntity() }
    )
