package team.aliens.dms.android.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.file.FileUploadManager
import team.aliens.dms.android.core.network.util.OkHttpClient
import team.aliens.dms.android.core.network.util.Retrofit
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

    // TODO: fix location
    @Provides
    @Singleton
    @FileUploadHttpClient
    fun provideFileUploadManager(
        @DefaultHttpLoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
        @DefaultHttpClient httpClient: OkHttpClient,
    ): FileUploadManager = FileUploadManager(
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
