package team.aliens.dms_android.app.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.data.repository.AuthRepositoryImpl
import team.aliens.dms_android.data.repository.FileRepositoryImpl
import team.aliens.dms_android.data.repository.MealRepositoryImpl
import team.aliens.dms_android.data.repository.NoticeRepositoryImpl
import team.aliens.dms_android.data.repository.NotificationRepositoryImpl
import team.aliens.dms_android.data.repository.PointRepositoryImpl
import team.aliens.dms_android.data.repository.RemainsRepositoryImpl
import team.aliens.dms_android.data.repository.SchoolRepositoryImpl
import team.aliens.dms_android.data.repository.StudentRepositoryImpl
import team.aliens.dms_android.data.repository.StudyRoomRepositoryImpl
import team.aliens.dms_android.data.repository.UserRepositoryImpl
import team.aliens.dms_android.domain.repository.AuthRepository
import team.aliens.dms_android.domain.repository.FileRepository
import team.aliens.dms_android.domain.repository.MealRepository
import team.aliens.dms_android.domain.repository.NoticeRepository
import team.aliens.dms_android.domain.repository.NotificationRepository
import team.aliens.dms_android.domain.repository.PointRepository
import team.aliens.dms_android.domain.repository.RemainsRepository
import team.aliens.dms_android.domain.repository.SchoolRepository
import team.aliens.dms_android.domain.repository.StudentRepository
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import team.aliens.dms_android.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFileRepository(impl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository

    @Binds
    @Singleton
    abstract fun bindNoticeRepository(impl: NoticeRepositoryImpl): NoticeRepository

    @Binds
    @Singleton
    abstract fun bindPointRepository(impl: PointRepositoryImpl): PointRepository

    @Binds
    @Singleton
    abstract fun bindRemainsRepository(impl: RemainsRepositoryImpl): RemainsRepository

    @Binds
    @Singleton
    abstract fun bindSchoolRepository(impl: SchoolRepositoryImpl): SchoolRepository

    @Binds
    @Singleton
    abstract fun bindStudentRepository(impl: StudentRepositoryImpl): StudentRepository

    @Binds
    @Singleton
    abstract fun bindStudyRoomRepository(impl: StudyRoomRepositoryImpl): StudyRoomRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository
}
