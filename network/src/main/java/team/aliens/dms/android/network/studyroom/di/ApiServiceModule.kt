package team.aliens.dms.android.network.studyroom.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.studyroom.apiservice.StudyRoomApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideStudyRoomApiService(@GlobalRetrofitClient retrofit: Retrofit): StudyRoomApiService =
        retrofit.create(StudyRoomApiService::class.java)
}
