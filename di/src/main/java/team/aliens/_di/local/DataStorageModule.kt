package team.aliens._di.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.local.datastore.storage.AuthDataStorage
import team.aliens.local.datastore.storage.AuthDataStorageImpl
import team.aliens.local.datastore.storage.SchoolDataStorage
import team.aliens.local.datastore.storage.SchoolDataStorageImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun provideAuthDataStorage(
        impl: AuthDataStorageImpl,
    ): AuthDataStorage

    @Binds
    @Singleton
    abstract fun provideStudentDataStorage(
        impl: SchoolDataStorageImpl,
    ): SchoolDataStorage
}
