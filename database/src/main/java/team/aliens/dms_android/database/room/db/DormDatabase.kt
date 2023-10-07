package team.aliens.dms_android.database.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.aliens.dms_android.database.room.common.RoomProperty
import team.aliens.dms_android.database.room.converter.StringListTypeConverter
import team.aliens.dms_android.database.room.converter.UuidTypeConverter
import team.aliens.dms_android.database.room.dao.MealDao
import team.aliens.dms_android.database.room.dao.NoticeDao
import team.aliens.dms_android.database.room.entity.MealEntity
import team.aliens.dms_android.database.room.entity.NoticeEntity

@Database(
    entities = [
        MealEntity::class,
        NoticeEntity::class,
    ],
    version = RoomProperty.Database.DbVersion,
    exportSchema = false,
)
@TypeConverters(
    value = [
        StringListTypeConverter::class,
        UuidTypeConverter::class,
    ],
)
abstract class DormDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    abstract fun noticeDao(): NoticeDao
}
