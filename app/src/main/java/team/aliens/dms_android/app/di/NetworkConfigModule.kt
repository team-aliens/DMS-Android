package team.aliens.dms_android.app.di

import team.aliens.dms_android.network.BuildConfig as NetworkBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.network.di.BaseUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = NetworkBuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideTokenReissueUrl(@BaseUrl baseUrl: String): String = "$baseUrl/auth/reissue"
}
