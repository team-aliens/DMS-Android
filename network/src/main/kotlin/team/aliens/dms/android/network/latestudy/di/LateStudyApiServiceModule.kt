package team.aliens.dms.android.network.latestudy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.network.latestudy.apiservice.LateStudyApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LateStudyApiServiceModule {

    @Provides
    @Singleton
    fun provideLateStudyApiService(
        retrofit: Retrofit,
    ): LateStudyApiService =
        retrofit.create(LateStudyApiService::class.java)
}
