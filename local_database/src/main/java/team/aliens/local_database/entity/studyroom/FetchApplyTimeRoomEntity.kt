package team.aliens.local_database.entity.studyroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ApplyTime")
data class FetchApplyTimeRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
