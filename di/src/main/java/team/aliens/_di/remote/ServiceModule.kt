package team.aliens._di.remote

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
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideFileService(
        retrofit: Retrofit,
    ): FileService {
        return retrofit.create(FileService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealService(
        retrofit: Retrofit,
    ): MealService {
        return retrofit.create(MealService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeService(
        retrofit: Retrofit,
    ): NoticeService {
        return retrofit.create(NoticeService::class.java)
    }

    @Provides
    @Singleton
    fun providePointService(
        retrofit: Retrofit,
    ): PointService {
        return retrofit.create(PointService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemainsService(
        retrofit: Retrofit,
    ): RemainsService {
        return retrofit.create(RemainsService::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolService(
        retrofit: Retrofit,
    ): SchoolService {
        return retrofit.create(SchoolService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentService(
        retrofit: Retrofit,
    ): StudentService {
        return retrofit.create(StudentService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudyRoomService(
        retrofit: Retrofit,
    ): StudyRoomService {
        return retrofit.create(StudyRoomService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit,
    ): UserService {
        return retrofit.create(UserService::class.java)
    }
}
