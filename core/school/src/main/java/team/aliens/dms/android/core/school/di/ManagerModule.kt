package team.aliens.dms.android.core.school.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.network.di.DefaultHttpClient
import team.aliens.dms.android.core.network.di.DefaultHttpLoggingInterceptor
import team.aliens.dms.android.core.school.FetchUrl
import team.aliens.dms.android.core.school.network.FeaturesFetchingManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ManagerModule {

    @Provides
    @Singleton
    fun provideFeaturesFetchingManager(
        @FetchUrl fetchUrl: String,
        @DefaultHttpLoggingInterceptor defaultHttpLoggingInterceptor: HttpLoggingInterceptor,
        @DefaultHttpClient baseHttpClient: OkHttpClient,
    ): FeaturesFetchingManager = FeaturesFetchingManager(
        fetchUrl = fetchUrl,
        httpLoggingInterceptor = defaultHttpLoggingInterceptor,
        baseHttpClient = baseHttpClient,
    )
}
