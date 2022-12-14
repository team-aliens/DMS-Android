package com.example.local_database.entity.notice

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.notice.NoticeDetailLocalEntity
import java.util.UUID

@Entity(tableName = TableName.Notice.NOTICE_DETAIL)
data class NoticeDetailRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var noticeId: UUID,
    val title: String,
    val content: String,
    val createAt: String,
)

fun NoticeDetailRoomEntity.toEntity() =
    NoticeDetailLocalEntity(
        title = title,
        content = content,
        createAt = createAt
    )
