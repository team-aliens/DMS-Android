package team.aliens.di.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.database.room.dao.MealDao
import team.aliens.dms_android.database.room.dao.NoticeDao
import team.aliens.dms_android.database.room.db.DormDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideMealDao(
        db: DormDatabase,
    ): MealDao {
        return db.mealDao()
    }

    @Provides
    @Singleton
    fun provideNoticeDao(
        db: DormDatabase,
    ): NoticeDao {
        return db.noticeDao()
    }
}
