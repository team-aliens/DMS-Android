package team.aliens.dms_android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms_android.core.jwt.datastore.JwtDataStoreDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindJwtDataStore(impl: JwtDataStoreDataSourceImpl): JwtDataStoreDataSource
}
