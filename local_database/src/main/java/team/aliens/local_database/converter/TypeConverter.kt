package team.aliens.local_database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import team.aliens.local_database.entity.mypage.PointListRoomEntity
import team.aliens.local_database.entity.notice.NoticeListRoomEntity

@ProvidedTypeConverter
class StringListTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<String>? {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImage(type: List<String>): String {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }
}

@ProvidedTypeConverter
class NoticeListTypeConverter(
    private val moshi: Moshi,
) {

    @TypeConverter
    fun fromString(value: String): List<NoticeListRoomEntity.NoticeLocalValue>? {
        val listType = Types.newParameterizedType(List::class.java,
            NoticeListRoomEntity.NoticeLocalValue::class.java)
        val adapter: JsonAdapter<List<NoticeListRoomEntity.NoticeLocalValue>> =
            moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<NoticeListRoomEntity.NoticeLocalValue>): String {
        val listType = Types.newParameterizedType(List::class.java,
            NoticeListRoomEntity.NoticeLocalValue::class.java)
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
        val listType = Types.newParameterizedType(List::class.java, PointListRoomEntity::class.java)
        val adapter: JsonAdapter<List<PointListRoomEntity>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(type: List<PointListRoomEntity>): String {
        val listType = Types.newParameterizedType(List::class.java, PointListRoomEntity::class.java)
        val adapter: JsonAdapter<List<PointListRoomEntity>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }
}
