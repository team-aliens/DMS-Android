package team.aliens.di

import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.datasource.declaration.LocalMyPageDataSource
import com.example.local_database.datasource.declaration.LocalNoticeDataSource
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.datasource.implementation.LocalMealDataSourceImpl
import com.example.local_database.datasource.implementation.LocalMyPageDataSourceImpl
import com.example.local_database.datasource.implementation.LocalNoticeDataSourceImpl
import com.example.local_database.datasource.implementation.LocalUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl
    ): LocalUserDataSource

    @Binds
    abstract fun provideLocalMealDataSource(
        localMealDataSourceImpl: LocalMealDataSourceImpl
    ): LocalMealDataSource

    @Binds
    abstract fun provideLocalNoticeDataSource(
        localNoticeDataSourceImpl: LocalNoticeDataSourceImpl
    ): LocalNoticeDataSource

    @Binds
    abstract fun provideLocalMyPageDataSource(
        localMyPageDataSourceImpl: LocalMyPageDataSourceImpl
    ): LocalMyPageDataSource
}
