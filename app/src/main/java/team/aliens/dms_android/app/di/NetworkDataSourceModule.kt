package team.aliens.dms_android.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.data.datasource.remote.RemoteAuthDataSource
import team.aliens.data.datasource.remote.RemoteFileDataSource
import team.aliens.data.datasource.remote.RemoteMealDataSource
import team.aliens.data.datasource.remote.RemoteNoticeDataSource
import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.data.datasource.remote.RemotePointDataSource
import team.aliens.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.data.datasource.remote.RemoteSchoolDataSource
import team.aliens.data.datasource.remote.RemoteStudentDataSource
import team.aliens.data.datasource.remote.RemoteStudyRoomDataSource
import team.aliens.data.datasource.remote.RemoteUserDataSource
import team.aliens.network.datasource.RemoteAuthDataSourceImpl
import team.aliens.network.datasource.RemoteFileDataSourceImpl
import team.aliens.network.datasource.RemoteMealDataSourceImpl
import team.aliens.network.datasource.RemoteNoticeDataSourceImpl
import team.aliens.network.datasource.RemoteNotificationDataSourceImpl
import team.aliens.network.datasource.RemotePointDataSourceImpl
import team.aliens.network.datasource.RemoteRemainsDataSourceImpl
import team.aliens.network.datasource.RemoteSchoolDataSourceImpl
import team.aliens.network.datasource.RemoteStudentDataSourceImpl
import team.aliens.network.datasource.RemoteStudyRoomDataSourceImpl
import team.aliens.network.datasource.RemoteUserDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkAuthDataSource(impl: RemoteAuthDataSourceImpl): RemoteAuthDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkFileDataSource(impl: RemoteFileDataSourceImpl): RemoteFileDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkMealDataSource(impl: RemoteMealDataSourceImpl): RemoteMealDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkNoticeDataSource(impl: RemoteNoticeDataSourceImpl): RemoteNoticeDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkPointDataSource(impl: RemotePointDataSourceImpl): RemotePointDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkRemainDataSource(impl: RemoteRemainsDataSourceImpl): RemoteRemainsDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkSchoolDataSource(impl: RemoteSchoolDataSourceImpl): RemoteSchoolDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkStudentDataSource(impl: RemoteStudentDataSourceImpl): RemoteStudentDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkStudyRoomDataSource(impl: RemoteStudyRoomDataSourceImpl): RemoteStudyRoomDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkUserDataSource(impl: RemoteUserDataSourceImpl): RemoteUserDataSource

    @Binds
    @Singleton
    abstract fun bindNetworkNotificationDataSource(impl: RemoteNotificationDataSourceImpl): RemoteNotificationDataSource
}
