package team.aliens.dms_android.app.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms_android.app.Debug
import team.aliens.remote.annotation.DefaultHttpLoggingInterceptor
import team.aliens.remote.annotation.DefaultHttpLoggingLevel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpInterceptorModule {

    @Provides
    @Singleton
    @DefaultHttpLoggingLevel
    fun provideHttpLoggingLevel(@Debug debug: Boolean): HttpLoggingInterceptor.Level = if (debug) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    @DefaultHttpLoggingInterceptor
    fun provideDefaultHttpLoggingInterceptor(@DefaultHttpLoggingLevel level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(level)
}
