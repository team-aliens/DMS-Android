package team.aliens.dms.android.core.database.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.ZoneOffset
import team.aliens.dms.android.core.database.DmsDatabase
import team.aliens.dms.android.core.database.converter.DateTypeConverter
import team.aliens.dms.android.core.database.converter.StringListTypeConverter
import team.aliens.dms.android.core.database.converter.UuidTypeConverter
import team.aliens.dms.android.core.database.dao.MealDao
import team.aliens.dms.android.core.database.dao.NoticeDao
import team.aliens.dms.android.core.database.util.addTypeConverters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDmsDataBase(
        @ApplicationContext context: Context,
        zoneOffset: ZoneOffset,
        moshi: Moshi,
    ): DmsDatabase = Room.databaseBuilder(
        context = context,
        klass = DmsDatabase::class.java,
        name = "dms-database",
    ).addTypeConverters(
        StringListTypeConverter(moshi),
        UuidTypeConverter(),
        DateTypeConverter(zoneOffset),
    ).build()

    @Provides
    @Singleton
    fun provideMealDao(db: DmsDatabase): MealDao = db.mealDao()

    @Provides
    @Singleton
    fun provideNoticeDao(db: DmsDatabase): NoticeDao = db.noticeDao()
}
