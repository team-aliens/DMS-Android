package team.aliens.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.repository.*
import team.aliens.domain.repository.*
import team.aliens.local_database.repository.LocalUserRepositoryImpl
import team.aliens.local_database.repository.meal.LocalMealRepositoryImpl
import team.aliens.local_database.repository.mypage.LocalMyPageRepositoryImpl
import team.aliens.local_database.repository.notice.LocalNoticeRepositoryImpl
import team.aliens.local_domain.repository.LocalUserRepository
import team.aliens.local_domain.repository.meal.LocalMealRepository
import team.aliens.local_domain.repository.mypage.LocalMyPageRepository
import team.aliens.local_domain.repository.notice.LocalNoticeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRemoteStudentsRepository(
        studentsRepositoryImpl: StudentsRepositoryImpl,
    ): StudentsRepository

    @Binds
    abstract fun providesRemoteUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun provideRemoteMealRepository(
        mealRepositoryImpl: MealRepositoryImpl,
    ): MealRepository

    @Binds
    abstract fun provideRemoteNoticeRepository(
        noticeRepositoryImpl: NoticeRepositoryImpl,
    ): NoticeRepository

    @Binds
    abstract fun provideRemoteSchoolsRepository(
        schoolsRepositoryImpl: SchoolsRepositoryImpl,
    ): SchoolsRepository

    @Binds
    abstract fun provideRemoteMyPageRepository(
        myPageRepositoryImpl: MyPageRepositoryImpl,
    ): MyPageRepository

    @Binds
    abstract fun provideRemoteStudyRoomRepository(
        studyRoomRepositoryImpl: StudyRoomRepositoryImpl,
    ): StudyRoomRepository

    @Binds
    abstract fun provideLocalUserRepository(
        localUserRepositoryImpl: LocalUserRepositoryImpl,
    ): LocalUserRepository

    @Binds
    abstract fun provideLocalMealRepository(
        localMealRepositoryImpl: LocalMealRepositoryImpl,
    ): LocalMealRepository

    @Binds
    abstract fun provideLocalNoticeRepository(
        localNoticeRepositoryImpl: LocalNoticeRepositoryImpl,
    ): LocalNoticeRepository

    @Binds
    abstract fun provideLocalMyPageRepository(
        localMyPageRepositoryImpl: LocalMyPageRepositoryImpl,
    ): LocalMyPageRepository

    @Binds
    @Singleton
    abstract fun bindsRemainRepository(
        remainRepositoryImpl: RemainRepositoryImpl,
    ): RemainRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepositoryImpl: FileRepositoryImpl,
    ): FileRepository
}
