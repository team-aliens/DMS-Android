package team.aliens.di.datasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.data.datasource.remote.RemoteAuthDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteFileDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteMealDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteNoticeDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.dms_android.data.datasource.remote.RemotePointDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteSchoolDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteStudentDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteStudyRoomDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteUserDataSource
import team.aliens.dms_android.network.datasource.*
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
        impl: RemoteStudentDataSourceImpl,
    ): RemoteStudentDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteStudyRoomDataSource(
        impl: RemoteStudyRoomDataSourceImpl,
    ): RemoteStudyRoomDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteUserDataSource(
        impl: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteNotificationDataSource(
        impl: RemoteNotificationDataSourceImpl,
    ): RemoteNotificationDataSource
}
