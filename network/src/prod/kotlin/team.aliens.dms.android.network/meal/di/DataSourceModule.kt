package team.aliens.dms.android.network.meal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.meal.datasource.NetworkMealDataSource
import team.aliens.dms.android.network.meal.datasource.NetworkMealDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMealDataSource(impl: NetworkMealDataSourceImpl): NetworkMealDataSource
}
