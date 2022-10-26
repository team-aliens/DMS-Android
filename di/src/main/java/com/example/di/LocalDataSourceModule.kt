package com.example.di

import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.datasource.implementation.LocalMealDataSourceImpl
import com.example.local_database.datasource.implementation.LocalUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl
    ): LocalUserDataSource

    @Binds
    abstract fun provideMealUserDataSource(
        localMealDataSourceImpl: LocalMealDataSourceImpl
    ): LocalMealDataSource
}