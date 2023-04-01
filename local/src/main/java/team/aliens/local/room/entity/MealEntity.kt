package team.aliens.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import team.aliens.local.room.common.RoomProperty.ColumnName
import team.aliens.local.room.common.RoomProperty.TableName

@Entity(
    tableName = TableName.Meal,
)
data class MealEntity(

    @PrimaryKey
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
)
