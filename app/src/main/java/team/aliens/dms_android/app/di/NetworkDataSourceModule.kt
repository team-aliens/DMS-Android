package team.aliens.dms_android.app.di

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
import team.aliens.dms.android.network.datasource.RemoteAuthDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteFileDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteMealDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteNoticeDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteNotificationDataSourceImpl
import team.aliens.dms.android.network.datasource.RemotePointDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteRemainsDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteSchoolDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteStudentDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteStudyRoomDataSourceImpl
import team.aliens.dms.android.network.datasource.RemoteUserDataSourceImpl
import javax.inject.Singleton


// TODO: remove
@Deprecated("No usage")
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
