package team.aliens.dms.android.core.jwt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindJwtDataStoreDataSource(impl: JwtDataStoreDataSourceImpl): JwtDataStoreDataSource
}
