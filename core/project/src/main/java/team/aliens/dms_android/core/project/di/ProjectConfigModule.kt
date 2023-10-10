package team.aliens.dms_android.core.project.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.project.BuildConfig
import team.aliens.dms_android.core.project.BuildType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ProjectConfigModule {

    @Provides
    @Singleton
    @Debug
    fun provideIsDebugEnabled(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideBuildType(): BuildType = BuildType.valueOf(BuildConfig.BUILD_TYPE)
}
