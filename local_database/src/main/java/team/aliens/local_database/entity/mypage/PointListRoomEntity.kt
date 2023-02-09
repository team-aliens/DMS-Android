package team.aliens.local_database.entity.mypage

import androidx.room.Entity
import androidx.room.PrimaryKey
import team.aliens.local_database.tablename.TableName
import team.aliens.local_domain.entity.mypage.PointListValueEntity
import java.util.*

@Entity(tableName = TableName.POINT)
data class PointListRoomEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var pointId: UUID,
    var date: String,
    var pointLocalType: String,
    var name: String,
    var score: Int,
)

internal fun PointListRoomEntity.toEntity() = PointListValueEntity(
    pointId = pointId,
    date = date,
    pointLocalType = pointLocalType,
    name = name,
    score = score,
)
