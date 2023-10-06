package team.aliens.dms_android.app.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.dms_android.app.Debug
import team.aliens.remote.annotation.DefaultHttpLoggingInterceptor
import team.aliens.remote.annotation.DefaultHttpLoggingLevel
import team.aliens.remote.http.AuthorizationInterceptor
import team.aliens.remote.http.IgnoreRequestWrapper
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
    fun provideDefaultHttpLoggingInterceptor(
        @DefaultHttpLoggingLevel level: HttpLoggingInterceptor.Level,
    ): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(level)

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(
        // TODO: remove origin class
        authorizationFacade: AuthorizationFacade,
        // TODO: remove origin class
        ignoreRequestWrapper: IgnoreRequestWrapper,
    ): AuthorizationInterceptor = AuthorizationInterceptor(
        authorizationFacade = authorizationFacade,
        ignoreRequestWrapper = ignoreRequestWrapper,
    )
}
