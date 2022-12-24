package com.example.local_database.entity.mypage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.mypage.PointListValueEntity
import org.threeten.bp.LocalDateTime
import java.util.UUID

@Entity(tableName = TableName.POINT)
data class PointListRoomEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var pointId: UUID,
    var date: String,
    var pointLocalType: String,
    var name: String,
    var score: Int,
)

internal fun PointListRoomEntity.toEntity() =
    PointListValueEntity(
        pointId = pointId,
        date = date,
        pointLocalType = pointLocalType,
        name = name,
        score = score,
    )
