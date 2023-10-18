package team.aliens.dms.android.core.jwt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.jwt.network.TokenReissueManager
import team.aliens.dms.android.core.network.di.DefaultHttpClient
import team.aliens.dms.android.core.network.di.DefaultHttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Provides
    @Singleton
    fun provideTokenReissueManager(
        @TokenReissueUrl reissueUrl: String,
        @DefaultHttpLoggingInterceptor defaultHttpLoggingInterceptor: HttpLoggingInterceptor,
        @DefaultHttpClient baseHttpClient: OkHttpClient,
    ): TokenReissueManager = TokenReissueManager(
        reissueUrl = reissueUrl,
        httpLoggingInterceptor = defaultHttpLoggingInterceptor,
        baseHttpClient = baseHttpClient,
    )
}
