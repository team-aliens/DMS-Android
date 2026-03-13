package team.aliens.dms.android.network.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.user.apiservice.UserApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideUserApiService(@GlobalRetrofitClient retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)
}
