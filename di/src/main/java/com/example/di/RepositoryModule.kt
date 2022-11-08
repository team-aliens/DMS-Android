package com.example.di

import com.example.auth_data.repository.SchoolsRepositoryImpl
import com.example.auth_data.repository.StudentsRepositoryImpl
import com.example.auth_data.repository.UserRepositoryImpl
import com.example.auth_domain.repository.SchoolsRepository
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.repository.UserRepository
import com.example.feature_data.repository.MealRepositoryImpl
import com.example.feature_data.repository.NoticeRepositoryImpl
import com.example.feature_domain.repository.MealRepository
import com.example.feature_domain.repository.NoticeRepository
import com.example.local_database.datasource.declaration.LocalNoticeDataSource
import com.example.local_database.datasource.implementation.LocalNoticeDataSourceImpl
import com.example.local_database.repository.LocalUserRepositoryImpl
import com.example.local_database.repository.meal.LocalMealRepositoryImpl
import com.example.local_database.repository.notice.LocalNoticeRepositoryImpl
import com.example.local_domain.repository.LocalUserRepository
import com.example.local_domain.repository.meal.LocalMealRepository
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
}
