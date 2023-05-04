package team.aliens.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.aliens.local.room.common.RoomProperty
import team.aliens.local.room.converter.StringListTypeConverter
import team.aliens.local.room.converter.UuidTypeConverter
import team.aliens.local.room.dao.MealDao
import team.aliens.local.room.dao.NoticeDao
import team.aliens.local.room.entity.MealEntity
import team.aliens.local.room.entity.NoticeEntity

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
