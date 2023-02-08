package team.aliens.local_database.entity.meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.meal.MealEntity

@Entity(tableName = TableName.MEAL_LIST)
data class MealRoomEntity(
    @PrimaryKey var date: String,
    var breakfast: List<String>,
    var lunch: List<String>,
    var dinner: List<String>,
)

internal fun MealRoomEntity.toEntity() =
    MealEntity(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )

internal fun List<MealRoomEntity>.toEntity() =
    map { it.toEntity() }
