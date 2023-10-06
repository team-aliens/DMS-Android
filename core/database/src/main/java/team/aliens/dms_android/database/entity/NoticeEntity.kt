package team.aliens.dms_android.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// TODO: 접근제한자 internal로 수정 필요
@Entity(tableName = "tbl_notices")
data class NoticeEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: UUID,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "created_at") val createdAt: String,
)
