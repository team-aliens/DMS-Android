package com.example.di

import com.example.data.repository.MealRepositoryImpl
import com.example.data.repository.MyPageRepositoryImpl
import com.example.data.repository.NoticeRepositoryImpl
import com.example.data.repository.SchoolsRepositoryImpl
import com.example.data.repository.StudentsRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.MealRepository
import com.example.domain.repository.MyPageRepository
import com.example.domain.repository.NoticeRepository
import com.example.domain.repository.SchoolsRepository
import com.example.domain.repository.StudentsRepository
import com.example.domain.repository.UserRepository
import com.example.local_database.repository.LocalUserRepositoryImpl
import com.example.local_database.repository.meal.LocalMealRepositoryImpl
import com.example.local_database.repository.mypage.LocalMyPageRepositoryImpl
import com.example.local_database.repository.notice.LocalNoticeRepositoryImpl
import com.example.local_domain.repository.LocalUserRepository
import com.example.local_domain.repository.meal.LocalMealRepository
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import com.example.local_domain.repository.notice.LocalNoticeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRemoteStudentsRepository(
        studentsRepositoryImpl: StudentsRepositoryImpl
    ): StudentsRepository

    @Binds
    abstract fun providesRemoteUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun provideRemoteMealRepository(
        mealRepositoryImpl: MealRepositoryImpl
    ): MealRepository

    @Binds
    abstract fun provideRemoteNoticeRepository(
        noticeRepositoryImpl: NoticeRepositoryImpl
    ): NoticeRepository
    
    @Binds
    abstract fun provideRemoteSchoolsRepository(
        schoolsRepositoryImpl: SchoolsRepositoryImpl
    ): SchoolsRepository

    @Binds
    abstract fun provideRemoteMyPageRepository(
        myPageRepositoryImpl: MyPageRepositoryImpl
    ): MyPageRepository

    @Binds
    abstract fun provideLocalUserRepository(
        localUserRepositoryImpl: LocalUserRepositoryImpl
    ): LocalUserRepository

    @Binds
    abstract fun provideLocalMealRepository(
        localMealRepositoryImpl: LocalMealRepositoryImpl
    ): LocalMealRepository

    @Binds
    abstract fun provideLocalNoticeRepository(
        localNoticeRepositoryImpl: LocalNoticeRepositoryImpl
    ): LocalNoticeRepository

    @Binds
    abstract fun provideLocalMyPageRepository(
        localMyPageRepositoryImpl: LocalMyPageRepositoryImpl
    ): LocalMyPageRepository
}
