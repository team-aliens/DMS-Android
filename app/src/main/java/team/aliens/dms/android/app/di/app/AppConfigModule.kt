package team.aliens.dms.android.app.di.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.ZoneOffset
import team.aliens.dms.android.app.BuildConfig
import team.aliens.dms.android.core.project.di.Debug
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    @Singleton
    @Debug
    fun provideDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideZoneOffset(): ZoneOffset = ZoneOffset.UTC
}
