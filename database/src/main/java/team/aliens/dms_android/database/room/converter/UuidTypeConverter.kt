package team.aliens.dms_android.database.room.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
class UuidTypeConverter {

    @TypeConverter
    fun fromUuid(
        value: UUID,
    ): String {
        return value.toString()
    }

    @TypeConverter
    fun toUuid(
        value: String,
    ): UUID {
        return UUID.fromString(value)
    }
}
