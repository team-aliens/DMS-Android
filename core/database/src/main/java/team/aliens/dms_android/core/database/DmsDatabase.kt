package team.aliens.dms_android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.aliens.dms_android.core.database.converter.DateTypeConverter
import team.aliens.dms_android.core.database.converter.StringListTypeConverter
import team.aliens.dms_android.core.database.converter.UuidTypeConverter
import team.aliens.dms_android.core.database.dao.MealDao
import team.aliens.dms_android.core.database.dao.NoticeDao
import team.aliens.dms_android.core.database.entity.MealEntity
import team.aliens.dms_android.core.database.entity.NoticeEntity

@Database(
    entities = [
        MealEntity::class,
        NoticeEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    value = [
        DateTypeConverter::class,
        StringListTypeConverter::class,
        UuidTypeConverter::class,
    ],
)
abstract class DmsDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun noticeDao(): NoticeDao
}
