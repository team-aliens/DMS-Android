package team.aliens.dms_android.app.di.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.local.room.common.RoomProperty
import team.aliens.local.room.converter.StringListTypeConverter
import team.aliens.local.room.converter.UuidTypeConverter
import team.aliens.local.room.db.DormDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDormDataBase(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): DormDatabase = Room.databaseBuilder(
        context = context,
        klass = DormDatabase::class.java,
        name = RoomProperty.Database.DbName,
    ).addTypeConverters(
        StringListTypeConverter(moshi),
        UuidTypeConverter(),
    ).build()
}

private fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTypeConverters(vararg converters: Any) =
    this.apply { converters.forEach { converter -> addTypeConverter(converter) } }
