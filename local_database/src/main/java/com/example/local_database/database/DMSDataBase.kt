package com.example.local_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local_database.converter.MealListTypeConverter
import com.example.local_database.dao.MealDao
import com.example.local_database.entity.meal.MealRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
    ], version = 1, exportSchema = false
)

@TypeConverters(
    value = [
        MealListTypeConverter::class
    ]
)

abstract class DMSDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}
