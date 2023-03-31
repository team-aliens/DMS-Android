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
import team.aliens.remote.interceptor.AuthorizationInterceptor
import team.aliens.remote.util.OkHttpClient
import team.aliens.remote.util.Retrofit
import javax.inject.Singleton
import team.aliens.remote.BuildConfig as RemoteBuildConfig

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (RemoteBuildConfig.DEBUG) {
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
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            // TODO add arguments
        )
    }

    @Provides
    @Singleton
    fun provideDefaultOkHttpClient(
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
    fun provideRetrofit(
        @DefaultOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {

        val clients = arrayOf(
            okHttpClient,
        )

        return Retrofit(
            clients = clients,
            baseUrl = "", // todo
            gsonConverterFactory = true,
        )
    }
}
*/
