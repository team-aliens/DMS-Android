package team.aliens.dms.android.app.di.project

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.ZoneOffset
import team.aliens.dms_android.app.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectConfigModule {

    @Provides
    @Singleton
    fun provideDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideZoneOffset(): ZoneOffset = ZoneOffset.UTC
}
