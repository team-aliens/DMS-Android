package team.aliens.local_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.aliens.local_database.converter.NoticeListTypeConverter
import team.aliens.local_database.converter.StringListTypeConverter
import team.aliens.local_database.dao.MealDao
import team.aliens.local_database.dao.NoticeDao
import team.aliens.local_database.dao.PointDao
import team.aliens.local_database.dao.StudyRoomDao
import team.aliens.local_database.entity.meal.MealRoomEntity
import team.aliens.local_database.entity.mypage.PointListRoomEntity
import team.aliens.local_database.entity.notice.NoticeDetailRoomEntity
import team.aliens.local_database.entity.notice.NoticeListRoomEntity
import team.aliens.local_database.entity.studyroom.FetchApplyTimeRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
        NoticeListRoomEntity::class,
        NoticeDetailRoomEntity::class,
        PointListRoomEntity::class,
        FetchApplyTimeRoomEntity::class,
    ], version = 1, exportSchema = false
)

@TypeConverters(
    value = [
        NoticeListTypeConverter::class,
        StringListTypeConverter::class,
    ]
)

abstract class DMSDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun noticeDao(): NoticeDao
    abstract fun pointDao(): PointDao
    abstract fun studyRoomDao(): StudyRoomDao
}
