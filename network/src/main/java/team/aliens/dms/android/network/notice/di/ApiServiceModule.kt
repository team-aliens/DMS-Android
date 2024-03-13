package team.aliens.dms.android.network.notice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.notice.apiservice.NoticeApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideNoticeApiService(@GlobalRetrofitClient retrofit: Retrofit): NoticeApiService =
        retrofit.create(NoticeApiService::class.java)
}
