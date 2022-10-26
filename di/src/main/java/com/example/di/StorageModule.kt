package com.example.di

import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.storage.implementation.UserDataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun provideUserDataStorage(
        userDataStorageImpl: UserDataStorageImpl
    ): UserDataStorage
}