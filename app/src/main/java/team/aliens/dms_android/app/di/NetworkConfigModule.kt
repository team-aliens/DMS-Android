package team.aliens.dms_android.app.di

import team.aliens.dms_android.remote.BuildConfig as RemoteBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.network.annotation.BaseUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = RemoteBuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideTokenReissueUrl(@BaseUrl baseUrl: String): String = "$baseUrl/auth/reissue"
}
