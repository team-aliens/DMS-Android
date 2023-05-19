package team.aliens.di.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.remote.apiservice.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit,
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFileService(
        retrofit: Retrofit,
    ): FileApiService {
        return retrofit.create(FileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealService(
        retrofit: Retrofit,
    ): MealApiService {
        return retrofit.create(MealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeService(
        retrofit: Retrofit,
    ): NoticeApiService {
        return retrofit.create(NoticeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePointService(
        retrofit: Retrofit,
    ): PointApiService {
        return retrofit.create(PointApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemainsService(
        retrofit: Retrofit,
    ): RemainsApiService {
        return retrofit.create(RemainsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolService(
        retrofit: Retrofit,
    ): SchoolApiService {
        return retrofit.create(SchoolApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentService(
        retrofit: Retrofit,
    ): StudentApiService {
        return retrofit.create(StudentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudyRoomService(
        retrofit: Retrofit,
    ): StudyRoomApiService {
        return retrofit.create(StudyRoomApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit,
    ): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}
