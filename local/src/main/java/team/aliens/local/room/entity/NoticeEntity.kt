package team.aliens.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import team.aliens.local.room.common.RoomProperty.ColumnName
import team.aliens.local.room.common.RoomProperty.TableName
import java.util.*

@Entity(
    tableName = TableName.Notice,
)
data class NoticeEntity(

    @PrimaryKey @ColumnInfo(
        name = ColumnName.Notices.Id,
    ) val id: UUID,

    @ColumnInfo(
        name = ColumnName.Notices.Title,
    ) val title: String,

    @ColumnInfo(
        name = ColumnName.Notices.Content,
    ) val content: String,

    @ColumnInfo(
        name = ColumnName.Notices.CreatedAt,
    ) val createdAt: String,
)
