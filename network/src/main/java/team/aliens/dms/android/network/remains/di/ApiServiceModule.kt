package team.aliens.dms.android.network.remains.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.network.remains.apiservice.RemainsApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideRemainsApiService(retrofit: Retrofit): RemainsApiService =
        retrofit.create(RemainsApiService::class.java)
}
