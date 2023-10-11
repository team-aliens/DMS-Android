package team.aliens.dms_android.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.aliens.dms_android.core.network.util.OkHttpClient
import team.aliens.dms_android.core.network.util.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Provides
    @Singleton
    @DefaultHttpClient
    fun provideDefaultHttpClient(
        @DefaultHttpLoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient(interceptors = arrayOf(httpLoggingInterceptor))

    @Provides
    @Singleton
    @GlobalHttpClient
    fun provideGlobalHttpClient(
        @DefaultHttpClient httpClient: OkHttpClient,
    ): OkHttpClient = httpClient.apply { /* apply global config */ }

    @Provides
    @Singleton
    @FileUploadHttpClient
    fun provideFileUploadHttpClient(
        @DefaultHttpLoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
        @DefaultHttpClient httpClient: OkHttpClient,
    ): OkHttpClient = team.aliens.dms_android.core.network.httpclient.FileUploadHttpClient(
        httpLoggingInterceptor = httpLoggingInterceptor,
        baseHttpClient = httpClient,
    )

    @Provides
    @Singleton
    fun provideRetrofit(
        @GlobalHttpClient okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit(
        clients = arrayOf(okHttpClient),
        baseUrl = baseUrl,
        gsonConverter = true,
    )
}
