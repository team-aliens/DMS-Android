package com.example.local_database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.local_database.entity.meal.MealRoomEntity
import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class MealListTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<MealRoomEntity.MealsRoomValue>? {
        val listType = Types.newParameterizedType(
            List::class.java,
            MealRoomEntity.MealsRoomValue::class.java
        )
        val adapter: JsonAdapter<List<MealRoomEntity.MealsRoomValue>> =
            moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<MealRoomEntity.MealsRoomValue>): String {
        val listType = Types.newParameterizedType(
            List::class.java, MealRoomEntity.MealsRoomValue::class.java
        )
        val adapter: JsonAdapter<List<MealRoomEntity.MealsRoomValue>> =
            moshi.adapter(listType)
        return adapter.toJson(type)
    }
}

@ProvidedTypeConverter
class NoticeListTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<NoticeListRoomEntity.NoticeLocalValue>? {
        val listType = Types.newParameterizedType(
            List::class.java,
            NoticeListRoomEntity.NoticeLocalValue::class.java
        )
        val adapter: JsonAdapter<List<NoticeListRoomEntity.NoticeLocalValue>> =
            moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<NoticeListRoomEntity.NoticeLocalValue>): String {
        val listType = Types.newParameterizedType(
            List::class.java, NoticeListRoomEntity.NoticeLocalValue::class.java
        )
        val adapter: JsonAdapter<List<NoticeListRoomEntity.NoticeLocalValue>> =
            moshi.adapter(listType)
        return adapter.toJson(type)
    }
}