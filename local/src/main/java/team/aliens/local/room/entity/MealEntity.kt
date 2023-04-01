package team.aliens.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import team.aliens.local.room.common.RoomProperty.ColumnName
import team.aliens.local.room.common.RoomProperty.TableName

@Entity(
    tableName = TableName.Meal,
)
internal data class MealEntity(

    @ColumnInfo(
        name = ColumnName.Meal.Date,
    ) val date: String,

    @ColumnInfo(
        name = ColumnName.Meal.Breakfast,
    ) val breakfast: List<String>,

    @ColumnInfo(
        name = ColumnName.Meal.Lunch,
    ) val lunch: List<String>,

    @ColumnInfo(
        name = ColumnName.Meal.Dinner,
    ) val dinner: List<String>,
) {

    @PrimaryKey(
        autoGenerate = true,
    )
    @ColumnInfo(
        name = ColumnName.Meal.Id
    )
    val id: Long = 0L
}
