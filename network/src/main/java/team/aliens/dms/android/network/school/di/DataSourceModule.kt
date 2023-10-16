package team.aliens.dms.android.network.school.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSource
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkSchoolDataSource(impl: NetworkSchoolDataSourceImpl): NetworkSchoolDataSource
}
