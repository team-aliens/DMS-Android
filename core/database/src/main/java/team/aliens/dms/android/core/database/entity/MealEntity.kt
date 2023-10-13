package team.aliens.dms.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_meals")
data class MealEntity(
    @PrimaryKey @ColumnInfo("date") val date: String,
    @ColumnInfo(name = "breakfast") val breakfast: List<String>,
    @ColumnInfo(name = "lunch") val lunch: List<String>,
    @ColumnInfo(name = "dinner") val dinner: List<String>,
)
