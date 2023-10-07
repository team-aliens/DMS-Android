package team.aliens.dms_android.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import team.aliens.domain.model.notice.Notice
import team.aliens.dms_android.database.room.common.RoomProperty.ColumnName
import team.aliens.dms_android.database.room.common.RoomProperty.TableName
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

internal fun NoticeEntity.toDomain(): Notice {
    return Notice(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

internal fun List<NoticeEntity>.toDomain(): List<Notice> {
    return this.map(NoticeEntity::toDomain)
}

internal fun Notice.toData(): NoticeEntity {
    return NoticeEntity(
        id = this.id!!, // fixme 고민 필요
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

internal fun Array<Notice>.toData(): Array<NoticeEntity> {
    return this.map(Notice::toData).toTypedArray()
}
