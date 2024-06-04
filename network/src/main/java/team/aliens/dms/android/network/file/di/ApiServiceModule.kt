package team.aliens.dms.android.network.file.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.file.apiservice.FileApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideFileApiService(@GlobalRetrofitClient retrofit: Retrofit): FileApiService =
        retrofit.create(FileApiService::class.java)
}
