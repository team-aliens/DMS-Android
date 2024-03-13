package team.aliens.dms.android.network.student.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.student.apiservice.StudentApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideStudentApiService(@GlobalRetrofitClient retrofit: Retrofit): StudentApiService =
        retrofit.create(StudentApiService::class.java)
}
