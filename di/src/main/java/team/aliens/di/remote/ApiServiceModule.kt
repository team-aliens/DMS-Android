package team.aliens.di.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms_android.network.apiservice.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideAuthApiService(
        retrofit: Retrofit,
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFileApiService(
        retrofit: Retrofit,
    ): FileApiService {
        return retrofit.create(FileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealApiService(
        retrofit: Retrofit,
    ): MealApiService {
        return retrofit.create(MealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeApiService(
        retrofit: Retrofit,
    ): NoticeApiService {
        return retrofit.create(NoticeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePointApiService(
        retrofit: Retrofit,
    ): PointApiService {
        return retrofit.create(PointApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemainsApiService(
        retrofit: Retrofit,
    ): RemainsApiService {
        return retrofit.create(RemainsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolApiService(
        retrofit: Retrofit,
    ): SchoolApiService {
        return retrofit.create(SchoolApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentApiService(
        retrofit: Retrofit,
    ): StudentApiService {
        return retrofit.create(StudentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudyRoomApiService(
        retrofit: Retrofit,
    ): StudyRoomApiService {
        return retrofit.create(StudyRoomApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(
        retrofit: Retrofit,
    ): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationApiService(
        retrofit: Retrofit,
    ): NotificationApiService {
        return retrofit.create(NotificationApiService::class.java)
    }
}
