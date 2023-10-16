package team.aliens.dms.android.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import team.aliens.dms.android.network._legacy.apiservice.FileApiService
import team.aliens.dms.android.network._legacy.apiservice.StudyRoomApiService
import team.aliens.dms.android.network._legacy.apiservice.UserApiService
import javax.inject.Singleton


// TODO: remove
@Deprecated("No usage")
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideFileApiService(retrofit: Retrofit): FileApiService =
        retrofit.create(FileApiService::class.java)

    @Provides
    @Singleton
    fun provideMealApiService(retrofit: Retrofit): MealApiService =
        retrofit.create(MealApiService::class.java)

    @Provides
    @Singleton
    fun provideNoticeApiService(retrofit: Retrofit): NoticeApiService =
        retrofit.create(NoticeApiService::class.java)

    @Provides
    @Singleton
    fun providePointApiService(retrofit: Retrofit): PointApiService =
        retrofit.create(PointApiService::class.java)

    @Provides
    @Singleton
    fun provideRemainsApiService(retrofit: Retrofit): RemainsApiService =
        retrofit.create(RemainsApiService::class.java)

    @Provides
    @Singleton
    fun provideSchoolApiService(retrofit: Retrofit): SchoolApiService =
        retrofit.create(SchoolApiService::class.java)

    @Provides
    @Singleton
    fun provideStudentApiService(retrofit: Retrofit): StudentApiService =
        retrofit.create(StudentApiService::class.java)

    @Provides
    @Singleton
    fun provideStudyRoomApiService(retrofit: Retrofit): StudyRoomApiService =
        retrofit.create(StudyRoomApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService =
        retrofit.create(NotificationApiService::class.java)
}
