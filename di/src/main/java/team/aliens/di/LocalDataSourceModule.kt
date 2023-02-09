package team.aliens.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.local_database.datasource.declaration.LocalMealDataSource
import team.aliens.local_database.datasource.declaration.LocalMyPageDataSource
import team.aliens.local_database.datasource.declaration.LocalNoticeDataSource
import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.datasource.implementation.LocalMealDataSourceImpl
import team.aliens.local_database.datasource.implementation.LocalMyPageDataSourceImpl
import team.aliens.local_database.datasource.implementation.LocalNoticeDataSourceImpl
import team.aliens.local_database.datasource.implementation.LocalUserDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl,
    ): LocalUserDataSource

    @Binds
    abstract fun provideLocalMealDataSource(
        localMealDataSourceImpl: LocalMealDataSourceImpl,
    ): LocalMealDataSource

    @Binds
    abstract fun provideLocalNoticeDataSource(
        localNoticeDataSourceImpl: LocalNoticeDataSourceImpl,
    ): LocalNoticeDataSource

    @Binds
    abstract fun provideLocalMyPageDataSource(
        localMyPageDataSourceImpl: LocalMyPageDataSourceImpl,
    ): LocalMyPageDataSource
}
