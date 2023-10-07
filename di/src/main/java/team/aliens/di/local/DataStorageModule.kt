package team.aliens.di.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.database.datastore.storage.AuthDataStorage
import team.aliens.dms_android.database.datastore.storage.AuthDataStorageImpl
import team.aliens.dms_android.database.datastore.storage.SchoolDataStorage
import team.aliens.dms_android.database.datastore.storage.SchoolDataStorageImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataStorage(
        impl: AuthDataStorageImpl,
    ): AuthDataStorage

    @Binds
    @Singleton
    abstract fun bindStudentDataStorage(
        impl: SchoolDataStorageImpl,
    ): SchoolDataStorage
}
