package com.example.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.local_database.entity.meal.MealRoomEntity
import com.example.local_database.tablename.TableName

@Dao
interface MealDao {

    @Query("SELECT * FROM ${TableName.MEAL_LIST}")
    suspend fun fetchMealList(): MealRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMealList(mealRoomEntity: MealRoomEntity)
}
