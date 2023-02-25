package team.aliens.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.remote.datasource.declaration.*
import team.aliens.data.remote.datasource.implementation.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideRemoteStudentsDataSource(
        remoteStudentsDataSourceImpl: RemoteStudentsDataSourceImpl,
    ): RemoteStudentsDataSource

    @Binds
    abstract fun provideRemoteUserDataSource(
        remoteUserDataSourceImpl: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource

    @Binds
    abstract fun provideRemoteMealDataSource(
        remoteMealDataSourceImpl: RemoteMealDataSourceImpl,
    ): RemoteMealDataSource

    @Binds
    abstract fun provideRemoteNoticeDataSource(
        remoteNoticeDataSourceImpl: RemoteNoticeDataSourceImpl,
    ): RemoteNoticeDataSource

    @Binds
    abstract fun provideRemoteSchoolsDataSource(
        remoteSchoolsDataSourceImpl: RemoteSchoolsDataSourceImpl,
    ): RemoteSchoolsDataSource

    @Binds
    abstract fun provideRemoteMyPageDataSource(
        remoteMyPageDataSourceImpl: RemoteMyPageDataSourceImpl,
    ): RemoteMyPageDataSource

    @Binds
    abstract fun provideRemoteStudyRoomDataSource(
        remoteStudyRoomDataSourceImpl: RemoteStudyRoomDataSourceImpl,
    ): RemoteStudyRoomDataSource

    @Binds
    @Singleton
    abstract fun provideRemoteRemainDataSource(
        remoteRemainDataSourceImpl: RemoteRemainDataSourceImpl,
    ): RemoteRemainDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteFileDataSource(
        remoteFileDataSourceImpl: RemoteFileDataSourceImpl,
    ): RemoteFileDataSource
}
