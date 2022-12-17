package com.example.local_database.entity.meal

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.local_database.tablename.TableName
import com.example.local_domain.entity.meal.MealEntity

@Entity(tableName = TableName.MEAL_LIST)
data class MealRoomEntity(
    @PrimaryKey val date: String,
    @Embedded val breakfast: List<String>,
    @Embedded val lunch: List<String>,
    @Embedded val dinner: List<String>,
)

internal fun MealRoomEntity.toEntity() =
    MealEntity(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )
