package team.aliens.dms.android.network.student.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.core.network.di.GlobalRetrofitClient
import team.aliens.dms.android.network.student.apiservice.StudentAuthApiService
import team.aliens.dms.android.network.student.apiservice.StudentProfileApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    @Singleton
    fun provideStudentAuthApiService(@GlobalRetrofitClient retrofit: Retrofit): StudentAuthApiService =
        retrofit.create(StudentAuthApiService::class.java)

    @Provides
    @Singleton
    fun provideStudentProfileApiService(@GlobalRetrofitClient retrofit: Retrofit): StudentProfileApiService =
        retrofit.create(StudentProfileApiService::class.java)
}
