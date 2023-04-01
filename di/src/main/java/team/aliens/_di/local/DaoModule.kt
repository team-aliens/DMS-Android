package team.aliens._di.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.local.room.dao.MealDao
import team.aliens.local.room.dao.NoticeDao
import team.aliens.local.room.db.DormDatabase
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
