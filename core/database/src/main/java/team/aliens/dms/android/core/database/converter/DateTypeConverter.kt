package team.aliens.dms.android.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import team.aliens.dms.android.shared.date.toLocalDate
import team.aliens.dms.android.shared.date.toLocalDateTime
import javax.inject.Inject

@ProvidedTypeConverter
internal class DateTypeConverter @Inject constructor(
    private val zoneOffset: ZoneOffset,
) {

    @TypeConverter
    fun longToLocalDate(value: Long): LocalDate = value.toLocalDate()

    @TypeConverter
    fun localDateToLong(value: LocalDate): Long = value.toEpochDay()

    @TypeConverter
    fun longToLocalDateTime(value: Long): LocalDateTime = value.toLocalDateTime(zoneOffset)

    @TypeConverter
    fun localDateTimeToLong(value: LocalDateTime): Long = value.toEpochSecond(zoneOffset)
}
