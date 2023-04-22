package team.aliens._di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data._repository.*
import team.aliens.domain._repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFileRepository(
        impl: FileRepositoryImpl,
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        impl: MealRepositoryImpl,
    ): MealRepository

    @Binds
    @Singleton
    abstract fun bindNoticeRepository(
        impl: NoticeRepositoryImpl,
    ): NoticeRepository

    @Binds
    @Singleton
    abstract fun bindPointRepository(
        impl: PointRepositoryImpl,
    ): PointRepository

    @Binds
    @Singleton
    abstract fun bindRemainsRepository(
        impl: RemainsRepositoryImpl,
    ): RemainsRepository

    @Binds
    @Singleton
    abstract fun bindSchoolRepository(
        impl: SchoolRepositoryImpl,
    ): SchoolRepository

    @Binds
    @Singleton
    abstract fun bindStudentRepository(
        impl: StudentRepositoryImpl,
    ): StudentRepository

    @Binds
    @Singleton
    abstract fun bindStudyRoomRepository(
        impl: StudyRoomRepositoryImpl,
    ): StudyRoomRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl,
    ): UserRepository
}
