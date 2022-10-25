package com.example.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.local_database.entity.meal.MealRoomEntity

@Dao
interface MealDao {

    @Query("SELECT * FROM mealList")
    suspend fun fetchMealList(): MealRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNoticeList(mealRoomEntity: MealRoomEntity)
}