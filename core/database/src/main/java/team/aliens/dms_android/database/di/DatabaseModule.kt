package team.aliens.dms_android.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.database.DmsDatabase
import team.aliens.dms_android.database.converter.StringListTypeConverter
import team.aliens.dms_android.database.converter.UuidTypeConverter
import team.aliens.dms_android.database.dao.MealDao
import team.aliens.dms_android.database.dao.NoticeDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDmsDataBase(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): DmsDatabase = Room.databaseBuilder(
        context = context,
        klass = DmsDatabase::class.java,
        name = "dms-database",
    ).addTypeConverters(
        StringListTypeConverter(moshi),
        UuidTypeConverter(),
    ).build()

    @Provides
    @Singleton
    fun provideMealDao(db: DmsDatabase): MealDao = db.mealDao()

    @Provides
    @Singleton
    fun provideNoticeDao(db: DmsDatabase): NoticeDao = db.noticeDao()
}

private fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTypeConverters(vararg converters: Any) =
    this.apply { converters.forEach { converter -> addTypeConverter(converter) } }
