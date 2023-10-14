package team.aliens.dms.android.database.meal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.database.meal.datasource.DatabaseMealDataSource
import team.aliens.dms.android.database.meal.datasource.DatabaseMealDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Provides
    @Singleton
    abstract fun provideDatabaseMealDataSource(impl: DatabaseMealDataSourceImpl): DatabaseMealDataSource
}
