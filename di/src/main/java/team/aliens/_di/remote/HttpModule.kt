package team.aliens._di.remote

/*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import team.aliens.data.intercepter.DefaultOkHttpClient
import team.aliens.remote.interceptor.AuthInterceptor
import team.aliens.remote.util.Retrofit
import javax.inject.Singleton
import team.aliens.presentation.BuildConfig as PresentationBuildConfig


@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (PresentationBuildConfig.DEBUG) {
                Level.BODY
            } else {
                Level.NONE
            }
        )
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        // TODO add arguments
    ): AuthInterceptor {
        return AuthInterceptor(
            // TODO add arguments
        )
    }

    @Provides
    @Singleton
    fun provideDefaultOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            httpLoggingInterceptor,
        ).addInterceptor(
            authInterceptor,
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @DefaultOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit(
            clients = arrayOf(
                okHttpClient,
            ),
            baseUrl = "",
            gsonConverterFactory = true,
        )
    }
}
*/
