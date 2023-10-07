package team.aliens.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.datasource.local.*
import team.aliens.dms_android.database.datasource.LocalAuthDataSourceImpl
import team.aliens.dms_android.database.datasource.LocalMealDataSourceImpl
import team.aliens.dms_android.database.datasource.LocalNoticeDataSourceImpl
import team.aliens.dms_android.database.datasource.LocalSchoolDataSourceImpl
import team.aliens.local.datasource.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalAuthDataSource(
        impl: LocalAuthDataSourceImpl,
    ): LocalAuthDataSource

    @Binds
    @Singleton
    abstract fun bindLocalMealDataSource(
        impl: LocalMealDataSourceImpl,
    ): LocalMealDataSource

    @Binds
    @Singleton
    abstract fun bindLocalNoticeDataSource(
        impl: LocalNoticeDataSourceImpl,
    ): LocalNoticeDataSource

    @Binds
    @Singleton
    abstract fun bindLocalSchoolDataSource(
        impl: LocalSchoolDataSourceImpl,
    ): LocalSchoolDataSource
}
