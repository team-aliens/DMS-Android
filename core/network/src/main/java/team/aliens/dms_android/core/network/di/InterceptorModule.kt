package team.aliens.dms_android.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms_android.network.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InterceptorModule {

    // TODO: remove in future
    @Provides
    @Singleton
    @Debug
    fun provideDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    @DefaultHttpLoggingLevel
    fun provideHttpLoggingLevel(
        // TODO: implement :core:project and inject debug
        @Debug debug: Boolean,
    ): HttpLoggingInterceptor.Level = if (debug) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    @DefaultHttpLoggingInterceptor
    fun provideDefaultHttpLoggingInterceptor(
        @DefaultHttpLoggingLevel level: HttpLoggingInterceptor.Level,
    ): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(level)
}
