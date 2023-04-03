package team.aliens._di.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.local.datastore.storage.AuthDataStorage
import team.aliens.local.datastore.storage.AuthDataStorageImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun provideAuthDataStorage(
        impl: AuthDataStorageImpl,
    ): AuthDataStorage
}
