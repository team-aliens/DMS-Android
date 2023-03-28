package team.aliens._di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data._datasource.remote.*
import team.aliens.remote.datasource.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteAuthDataSource(
        impl: RemoteAuthDataSourceImpl,
    ): RemoteAuthDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteFileDataSource(
        impl: RemoteFileDataSourceImpl,
    ): RemoteFileDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteMealDataSource(
        impl: RemoteMealDataSourceImpl,
    ): RemoteMealDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteNoticeDataSource(
        impl: RemoteNoticeDataSourceImpl,
    ): RemoteNoticeDataSource

    @Binds
    @Singleton
    abstract fun bindsRemotePointDataSource(
        impl: RemotePointDataSourceImpl,
    ): RemotePointDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteRemainDataSource(
        impl: RemoteRemainsDataSourceImpl,
    ): RemoteRemainsDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteSchoolDataSource(
        impl: RemoteSchoolDataSourceImpl,
    ): RemoteSchoolDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteStudentDataSource(
        impl: RemoteSchoolDataSourceImpl,
    ): RemoteSchoolDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteStudyRoomDataSource(
        impl: RemoteStudyRoomDataSourceImpl,
    ): RemoteStudyRoomDataSource
}
