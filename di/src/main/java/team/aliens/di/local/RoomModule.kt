package team.aliens.di.local
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.database.room.common.RoomProperty
import team.aliens.dms_android.database.room.converter.StringListTypeConverter
import team.aliens.dms_android.database.room.converter.UuidTypeConverter
import team.aliens.dms_android.database.room.db.DormDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDormDataBase(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): DormDatabase {
        return Room.databaseBuilder(
            context,
            DormDatabase::class.java,
            RoomProperty.Database.DbName,
        ).addTypeConverter(
            StringListTypeConverter(
                moshi = moshi,
            ),
        ).addTypeConverter(
            UuidTypeConverter(),
        ).build()
    }
}
