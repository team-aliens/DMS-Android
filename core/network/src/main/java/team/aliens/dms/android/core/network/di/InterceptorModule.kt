package team.aliens.dms.android.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms_android.core.project.di.Debug
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InterceptorModule {

    @Provides
    @Singleton
    @DefaultHttpLoggingLevel
    fun provideHttpLoggingLevel(
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
