package team.aliens.dms.android.network.student.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSource
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkStudentDataSource(impl: NetworkStudentDataSourceImpl): NetworkStudentDataSource
}
