package team.aliens.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.local_database.converter.NoticeListTypeConverter
import team.aliens.local_database.converter.StringListTypeConverter
import team.aliens.local_database.dao.MealDao
import team.aliens.local_database.dao.NoticeDao
import team.aliens.local_database.dao.PointDao
import team.aliens.local_database.database.DMSDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun provideDMSDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): DMSDataBase = Room.databaseBuilder(context, DMSDataBase::class.java, "DMSDataBase")
        .addTypeConverter(NoticeListTypeConverter(moshi))
        .addTypeConverter(StringListTypeConverter(moshi)).build()

    @Provides
    fun provideMealDao(
        dmsDataBase: DMSDataBase,
    ): MealDao = dmsDataBase.mealDao()

    @Provides
    fun provideNoticeDao(
        dmsDataBase: DMSDataBase,
    ): NoticeDao = dmsDataBase.noticeDao()

    @Provides
    fun providePointDao(
        dmsDataBase: DMSDataBase,
    ): PointDao = dmsDataBase.pointDao()
}
