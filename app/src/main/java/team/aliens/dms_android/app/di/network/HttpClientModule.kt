package team.aliens.dms_android.app.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.aliens.remote.annotation.BaseUrl
import team.aliens.remote.annotation.DefaultHttpClient
import team.aliens.remote.http.AuthorizationInterceptor
import team.aliens.remote.util.OkHttpClient
import team.aliens.remote.util.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    @DefaultHttpClient
    fun provideGlobalHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient = OkHttpClient(
        interceptors = arrayOf(httpLoggingInterceptor, authorizationInterceptor),
    )

    /* TODO: implement TokenReissueHttpClient
    @Provides
    @Singleton
    @TokenReissueOkHttpClient
    fun provideTokenReissueHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @TokenReissueUrl tokenReissueUrl: String,
    ) = */

    @Provides
    @Singleton
    fun provideRetrofit(
        @DefaultHttpClient okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit(
        clients = arrayOf(okHttpClient),
        baseUrl = baseUrl,
        gsonConverter = true,
    )
}
