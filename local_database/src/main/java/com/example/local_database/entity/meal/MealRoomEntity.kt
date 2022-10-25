package com.example.local_database.entity.meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.local_domain.entity.meal.MealEntity
import java.time.LocalDateTime

@Entity(tableName = "mealList")
data class MealRoomEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val meals: List<MealsRoomValue>,
) {
    data class MealsRoomValue(
        val date: LocalDateTime,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}

private fun MealRoomEntity.MealsRoomValue.toEntity() =
    MealEntity.MealValues(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )

fun MealRoomEntity.toEntity() =
    MealEntity(
        meals = meals.map { it.toEntity() }
    )