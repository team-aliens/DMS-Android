package team.aliens.dms.android.network.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.network.auth.apiservice.AuthApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)
}
