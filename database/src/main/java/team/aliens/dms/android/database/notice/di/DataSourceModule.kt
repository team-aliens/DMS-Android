package team.aliens.dms.android.database.notice.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.database.notice.datasource.DatabaseNoticeDataSource
import team.aliens.dms.android.database.notice.datasource.DatabaseNoticeDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideDatabaseNoticeDataSource(impl: DatabaseNoticeDataSourceImpl): DatabaseNoticeDataSource
}
