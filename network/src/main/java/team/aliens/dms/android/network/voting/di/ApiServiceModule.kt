package team.aliens.dms.android.network.voting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.voting.apiservice.VotingApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideVotingApiService(@GlobalRetrofitClient retrofit: Retrofit): VotingApiService =
        retrofit.create(VotingApiService::class.java)
}
