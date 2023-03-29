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
    abstract fun bindRemoteAuthDataSource(
        impl: RemoteAuthDataSourceImpl,
    ): RemoteAuthDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteFileDataSource(
        impl: RemoteFileDataSourceImpl,
    ): RemoteFileDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteMealDataSource(
        impl: RemoteMealDataSourceImpl,
    ): RemoteMealDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteNoticeDataSource(
        impl: RemoteNoticeDataSourceImpl,
    ): RemoteNoticeDataSource

    @Binds
    @Singleton
    abstract fun bindRemotePointDataSource(
        impl: RemotePointDataSourceImpl,
    ): RemotePointDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteRemainDataSource(
        impl: RemoteRemainsDataSourceImpl,
    ): RemoteRemainsDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteSchoolDataSource(
        impl: RemoteSchoolDataSourceImpl,
    ): RemoteSchoolDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteStudentDataSource(
        impl: RemoteSchoolDataSourceImpl,
    ): RemoteSchoolDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteStudyRoomDataSource(
        impl: RemoteStudyRoomDataSourceImpl,
    ): RemoteStudyRoomDataSource
}
