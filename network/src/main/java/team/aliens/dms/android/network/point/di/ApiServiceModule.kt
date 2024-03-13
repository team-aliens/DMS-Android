package team.aliens.dms.android.network.point.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.point.apiservice.PointApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun providePointApiService(@GlobalRetrofitClient retrofit: Retrofit): PointApiService =
        retrofit.create(PointApiService::class.java)
}
