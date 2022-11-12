package com.example.local_database.entity.mypage

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.mypage.PointListEntity
import java.util.UUID

@Entity(tableName = TableName.POINT)
data class PointListRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pointId: UUID,
    val date: String,
    val pointLocalType: String,
    val name: String,
    val score: Int,
)

fun PointListRoomEntity.toEntity() =
    PointListEntity(
        pointId = pointId,
        date = date,
        pointLocalType = pointLocalType,
        name = name,
        score = score,
    )

