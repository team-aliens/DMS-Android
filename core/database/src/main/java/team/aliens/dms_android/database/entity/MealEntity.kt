package team.aliens.dms_android.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: 접근제한자 internal로 수정 필요
@Entity(tableName = "tbl_meals")
data class MealEntity(
    @PrimaryKey @ColumnInfo("date") val date: String,
    @ColumnInfo(name = "breakfast") val breakfast: List<String>,
    @ColumnInfo(name = "lunch") val lunch: List<String>,
    @ColumnInfo(name = "dinner") val dinner: List<String>,
)
