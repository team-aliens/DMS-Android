package team.aliens.dms.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "tbl_meals")
data class MealEntity(
    @PrimaryKey
    @ColumnInfo("date")
    val date: LocalDate,
    @ColumnInfo(name = "breakfast") val breakfast: List<String>,
    @ColumnInfo(name = "kcal_breakfast") val kcalOfBreakfast: String?,
    @ColumnInfo(name = "lunch") val lunch: List<String>,
    @ColumnInfo(name = "kcal_lunch") val kcalOfLunch: String?,
    @ColumnInfo(name = "dinner") val dinner: List<String>,
    @ColumnInfo(name = "kcal_dinner") val kcalOfDinner: String?,
)
