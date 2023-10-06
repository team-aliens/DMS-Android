package team.aliens.dms_android.app.di.project

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.app.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectConfigModule {

    @Provides
    @Singleton
    fun provideDebug(): Boolean = BuildConfig.DEBUG
}
