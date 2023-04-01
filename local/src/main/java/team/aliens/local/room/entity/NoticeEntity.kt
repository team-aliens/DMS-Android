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
        name = ColumnName.Notice.Id,
    ) val id: UUID,

    @ColumnInfo(
        name = ColumnName.Notice.Title,
    ) val title: String,

    @ColumnInfo(
        name = ColumnName.Notice.Content,
    ) val content: String?,

    @ColumnInfo(
        name = ColumnName.Notice.CreatedAt,
    ) val createdAt: String,
)
