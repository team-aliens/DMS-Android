package com.example.local_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local_database.converter.MealListTypeConverter
import com.example.local_database.converter.NoticeListTypeConverter
import com.example.local_database.dao.MealDao
import com.example.local_database.dao.NoticeDao
import com.example.local_database.entity.meal.MealRoomEntity
import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
        NoticeListRoomEntity::class,
        NoticeDetailRoomEntity::class,
    ], version = 1, exportSchema = false
)

@TypeConverters(
    value = [
        MealListTypeConverter::class,
        NoticeListTypeConverter::class,
    ]
)

abstract class DMSDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun noticeDao(): NoticeDao
}
