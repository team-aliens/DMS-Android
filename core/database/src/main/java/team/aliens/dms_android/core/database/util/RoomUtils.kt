package team.aliens.dms_android.core.database.util

import androidx.room.RoomDatabase

internal fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTypeConverters(vararg converters: Any) =
    this.apply { converters.forEach { converter -> addTypeConverter(converter) } }
