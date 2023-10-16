package team.aliens.dms.android.network.studyroom.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.studyroom.datasource.NetworkStudyRoomDataSource
import team.aliens.dms.android.network.studyroom.datasource.NetworkStudyRoomDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkStudyRoomDataSource(impl: NetworkStudyRoomDataSourceImpl): NetworkStudyRoomDataSource
}
