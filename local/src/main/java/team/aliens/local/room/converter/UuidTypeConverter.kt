package team.aliens.local.room.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
internal class UuidTypeConverter {

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
