package team.aliens.dms.android.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.data.datasource.local.LocalAuthDataSource
import team.aliens.dms.android.data.datasource.local.LocalMealDataSource
import team.aliens.dms.android.data.datasource.local.LocalNoticeDataSource
import team.aliens.dms.android.data.datasource.local.LocalSchoolDataSource
import team.aliens.dms.android.database.datasource.LocalAuthDataSourceImpl
import team.aliens.dms.android.database.datasource.LocalMealDataSourceImpl
import team.aliens.dms.android.database.datasource.LocalNoticeDataSourceImpl
import team.aliens.dms.android.database.datasource.LocalSchoolDataSourceImpl
import javax.inject.Singleton


// TODO: remove
@Deprecated("No usage")
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
