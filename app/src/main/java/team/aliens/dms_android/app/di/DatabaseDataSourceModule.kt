package team.aliens.dms_android.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.data.datasource.local.LocalMealDataSource
import team.aliens.data.datasource.local.LocalNoticeDataSource
import team.aliens.data.datasource.local.LocalSchoolDataSource
import team.aliens.local.datasource.LocalAuthDataSourceImpl
import team.aliens.local.datasource.LocalMealDataSourceImpl
import team.aliens.local.datasource.LocalNoticeDataSourceImpl
import team.aliens.local.datasource.LocalSchoolDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalAuthDataSource(impl: LocalAuthDataSourceImpl): LocalAuthDataSource

    @Binds
    @Singleton
    abstract fun bindLocalMealDataSource(impl: LocalMealDataSourceImpl): LocalMealDataSource

    @Binds
    @Singleton
    abstract fun bindLocalNoticeDataSource(impl: LocalNoticeDataSourceImpl): LocalNoticeDataSource

    @Binds
    @Singleton
    abstract fun bindLocalSchoolDataSource(impl: LocalSchoolDataSourceImpl): LocalSchoolDataSource
}
