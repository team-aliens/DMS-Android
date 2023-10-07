package team.aliens.di.remote

import team.aliens.dms_android.remote.BuildConfig as RemoteBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.dms_android.di.BuildConfig
import team.aliens.dms_android.network.annotation.BaseUrl
import team.aliens.dms_android.network.annotation.DefaultHttpClient
import team.aliens.dms_android.network.annotation.TokenReissueHttpClient
import team.aliens.dms_android.network.annotation.TokenReissueUrl
import team.aliens.network.http.AuthorizationInterceptor
import team.aliens.network.http.IgnoreRequestWrapper
import team.aliens.network.http.TokenReissueManagerImpl
import team.aliens.network.util.OkHttpClient
import team.aliens.network.util.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    @Provides
    @Singleton
    @team.aliens.dms_android.network.annotation.BaseUrl
    fun provideBaseUrl(): String {
        return RemoteBuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    @team.aliens.dms_android.network.annotation.TokenReissueUrl
    fun provideTokenReissueUrl(
        @team.aliens.dms_android.network.annotation.BaseUrl baseUrl: String,
    ): String {
        return "$baseUrl/auth/reissue"
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        authorizationFacade: AuthorizationFacade,
        ignoreRequestWrapper: IgnoreRequestWrapper,
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            authorizationFacade = authorizationFacade,
            ignoreRequestWrapper = ignoreRequestWrapper,
        )
    }

    @Provides
    @Singleton
    @team.aliens.dms_android.network.annotation.DefaultHttpClient
    fun provideGlobalOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient {

        val interceptors = arrayOf(
            httpLoggingInterceptor,
            authorizationInterceptor,
        )

        return OkHttpClient(
            interceptors = interceptors,
        )
    }

    @Provides
    @Singleton
    @team.aliens.dms_android.network.annotation.TokenReissueHttpClient
    fun provideTokenReissueOkHttpClient(
        @team.aliens.dms_android.network.annotation.TokenReissueUrl tokenReissueUrl: String,
    ): TokenReissueManagerImpl {
        return TokenReissueManagerImpl(
            reissueUrl = tokenReissueUrl,
        )
    }

    @Provides
    @Singleton
    fun provideIgnoreRequestWrapper(): IgnoreRequestWrapper {
        return IgnoreRequestWrapper
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @team.aliens.dms_android.network.annotation.DefaultHttpClient okHttpClient: OkHttpClient,
        @team.aliens.dms_android.network.annotation.BaseUrl baseUrl: String,
    ): Retrofit {

        val clients = arrayOf(
            okHttpClient,
        )

        return Retrofit(
            clients = clients,
            baseUrl = baseUrl,
            gsonConverter = true,
        )
    }
}
