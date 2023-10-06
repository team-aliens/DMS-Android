package team.aliens.dms_android.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.UUID

@ProvidedTypeConverter
internal class UuidTypeConverter {

    @TypeConverter
    fun jsonToUuid(value: String): UUID = UUID.fromString(value)

    @TypeConverter
    fun uuidToJson(value: UUID): String = value.toString()
}
