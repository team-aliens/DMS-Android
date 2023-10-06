package team.aliens.dms_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.aliens.dms_android.database.converter.StringListTypeConverter
import team.aliens.dms_android.database.converter.UuidTypeConverter
import team.aliens.dms_android.database.dao.MealDao
import team.aliens.dms_android.database.dao.NoticeDao
import team.aliens.dms_android.database.entity.MealEntity
import team.aliens.dms_android.database.entity.NoticeEntity

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
        StringListTypeConverter::class,
        UuidTypeConverter::class,
    ],
)
internal abstract class DmsDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun noticeDao(): NoticeDao
}
