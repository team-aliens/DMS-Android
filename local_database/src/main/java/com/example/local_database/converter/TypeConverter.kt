package com.example.local_database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.local_database.entity.meal.MealRoomEntity
import com.example.local_database.entity.mypage.PointListRoomEntity
import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class MealTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<MealRoomEntity>? {
        val listType = Types.newParameterizedType(
            List::class.java,
            MealRoomEntity::class.java
        )
        val adapter: JsonAdapter<List<MealRoomEntity>> =
            moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<MealRoomEntity>): String {
        val listType = Types.newParameterizedType(
            List::class.java, MealRoomEntity::class.java
        )
        val adapter: JsonAdapter<List<MealRoomEntity>> =
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

@ProvidedTypeConverter
class PointListTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<PointListRoomEntity>? {
        val listType = Types.newParameterizedType(
            List::class.java,
            PointListRoomEntity::class.java
        )
        val adapter: JsonAdapter<List<PointListRoomEntity>> =
            moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<PointListRoomEntity>): String {
        val listType = Types.newParameterizedType(
            List::class.java, PointListRoomEntity::class.java
        )
        val adapter: JsonAdapter<List<PointListRoomEntity>> =
            moshi.adapter(listType)
        return adapter.toJson(type)
    }
}
