package team.aliens.dms.android.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.file.FileUploadManager
import team.aliens.dms.android.core.network.httpclient.DefaultInterceptors
import team.aliens.dms.android.core.network.httpclient.GlobalInterceptors
import team.aliens.dms.android.core.network.util.OkHttpClient
import team.aliens.dms.android.core.network.util.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Provides
    @Singleton
    @DefaultHttpClient
    fun provideDefaultHttpClient(defaultInterceptors: DefaultInterceptors): OkHttpClient =
        OkHttpClient(interceptors = defaultInterceptors.interceptors)

    @Provides
    @Singleton
    @GlobalHttpClient
    fun provideGlobalHttpClient(
        @DefaultHttpClient baseHttpClient: OkHttpClient,
        globalInterceptors: GlobalInterceptors,
    ): OkHttpClient = baseHttpClient.newBuilder().apply {
        globalInterceptors.interceptors.forEach(this::addInterceptor)
    }.build()

    @Provides
    @Singleton
    @GlobalRetrofitClient
    fun provideGlobalRetrofitClient(
        @GlobalHttpClient globalOkHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit(
        client = globalOkHttpClient,
        baseUrl = baseUrl,
    )

    @Provides
    @Singleton
    @DefaultRetrofitClient
    fun provideDefaultRetrofitClient(
        @DefaultHttpClient defaultHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit(
        client = defaultHttpClient,
        baseUrl = baseUrl,
        gsonConverter = true,
    )

    // TODO: fix location
    @Provides
    @Singleton
    fun provideFileUploadManager(
        @DefaultHttpLoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
        @DefaultHttpClient httpClient: OkHttpClient,
    ): FileUploadManager = FileUploadManager(
        httpLoggingInterceptor = httpLoggingInterceptor,
        baseHttpClient = httpClient,
    )
}
