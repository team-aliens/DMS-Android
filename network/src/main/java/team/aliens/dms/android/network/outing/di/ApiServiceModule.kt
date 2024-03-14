package team.aliens.dms.android.network.outing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.outing.apiservice.OutingApiService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiServiceModule {

    @Singleton
    @Provides
    fun provideOutingApiService(
        @GlobalRetrofitClient retrofit: Retrofit,
    ): OutingApiService = retrofit.create(OutingApiService::class.java)
}
