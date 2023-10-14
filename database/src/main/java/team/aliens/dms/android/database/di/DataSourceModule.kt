package team.aliens.dms.android.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.database.datasource.DatabaseMealDataSource
import team.aliens.dms.android.database.datasource.DatabaseMealDataSourceImpl
import team.aliens.dms.android.database.datasource.DatabaseNoticeDataSource
import team.aliens.dms.android.database.datasource.DatabaseNoticeDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Provides
    @Singleton
    abstract fun provideDatabaseMealDataSource(impl: DatabaseMealDataSourceImpl): DatabaseMealDataSource

    @Provides
    @Singleton
    abstract fun provideDatabaseNoticeDataSource(impl: DatabaseNoticeDataSourceImpl): DatabaseNoticeDataSource
}
