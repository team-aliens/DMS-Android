package com.example.local_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local_database.converter.NoticeListTypeConverter
import com.example.local_database.converter.StringListTypeConverter
import com.example.local_database.dao.MealDao
import com.example.local_database.dao.NoticeDao
import com.example.local_database.dao.PointDao
import com.example.local_database.entity.meal.MealRoomEntity
import com.example.local_database.entity.mypage.PointListRoomEntity
import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
        NoticeListRoomEntity::class,
        NoticeDetailRoomEntity::class,
        PointListRoomEntity::class,
    ], version = 1, exportSchema = false
)

@TypeConverters(
    value = [
        NoticeListTypeConverter::class,
        StringListTypeConverter::class,
    ]
)

abstract class DMSDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun noticeDao(): NoticeDao
    abstract fun pointDao(): PointDao
}
