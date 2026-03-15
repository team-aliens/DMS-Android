package team.aliens.dms.android.network.notification.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.notification.apiservice.NotificationApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideNotificationApiService(
        @GlobalRetrofitClient retrofit: Retrofit,
    ): NotificationApiService = retrofit.create(NotificationApiService::class.java)
}
