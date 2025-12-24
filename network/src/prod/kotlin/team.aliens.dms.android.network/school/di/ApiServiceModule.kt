package team.aliens.dms.android.network.school.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.school.apiservice.SchoolApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideSchoolApiService(@GlobalRetrofitClient retrofit: Retrofit): SchoolApiService =
        retrofit.create(SchoolApiService::class.java)
}
