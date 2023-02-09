package team.aliens.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.local_database.storage.declaration.MyPageDataStorage
import team.aliens.local_database.storage.declaration.UserDataStorage
import team.aliens.local_database.storage.implementation.MyPageDataStorageImpl
import team.aliens.local_database.storage.implementation.UserDataStorageImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun provideUserDataStorage(
        userDataStorageImpl: UserDataStorageImpl,
    ): UserDataStorage

    @Binds
    abstract fun provideMyPageDataStorage(
        myPageDataStorageImpl: MyPageDataStorageImpl,
    ): MyPageDataStorage
}
