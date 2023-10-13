package team.aliens.dms.android.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.database.datastore.storage.AuthDataStorage
import team.aliens.dms.android.database.datastore.storage.AuthDataStorageImpl
import team.aliens.dms.android.database.datastore.storage.SchoolDataStorage
import team.aliens.dms.android.database.datastore.storage.SchoolDataStorageImpl
import javax.inject.Singleton

// TODO: remove
@Deprecated("No usage")
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataStorage(impl: AuthDataStorageImpl): AuthDataStorage

    @Binds
    @Singleton
    abstract fun bindStudentDataStorage(impl: SchoolDataStorageImpl): SchoolDataStorage
}
