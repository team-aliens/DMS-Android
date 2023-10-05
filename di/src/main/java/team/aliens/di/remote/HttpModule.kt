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
import team.aliens.remote.annotation.BaseUrl
import team.aliens.remote.annotation.GlobalOkHttpClient
import team.aliens.remote.annotation.TokenReissueOkHttpClient
import team.aliens.remote.annotation.TokenReissueUrl
import team.aliens.remote.http.AuthorizationInterceptor
import team.aliens.remote.http.IgnoreRequestWrapper
import team.aliens.remote.http.TokenReissueManagerImpl
import team.aliens.remote.util.OkHttpClient
import team.aliens.remote.util.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            RemoteBuildConfig.DEV_BASE_URL
        } else {
            RemoteBuildConfig.PROD_BASE_URL
        }
    }

    @Provides
    @Singleton
    @TokenReissueUrl
    fun provideTokenReissueUrl(
        @BaseUrl baseUrl: String,
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
    @GlobalOkHttpClient
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
    @TokenReissueOkHttpClient
    fun provideTokenReissueOkHttpClient(
        @TokenReissueUrl tokenReissueUrl: String,
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
        @GlobalOkHttpClient okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit {

        val clients = arrayOf(
            okHttpClient,
        )

        return Retrofit(
            clients = clients,
            baseUrl = baseUrl,
            gsonConverter = true,
            rxJavaCallAdapter = true,
        )
    }
}
