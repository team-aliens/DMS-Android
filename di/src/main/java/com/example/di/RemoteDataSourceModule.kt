package com.example.di

import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import com.example.data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.data.remote.datasource.declaration.RemoteNoticeDataSource
import com.example.data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.datasource.declaration.RemoteUserDataSource
import com.example.data.remote.datasource.implementation.RemoteMealDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteMyPageDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteNoticeDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteSchoolsDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteStudentsDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteStudyRoomDataSourceImpl
import com.example.data.remote.datasource.implementation.RemoteUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideRemoteStudentsDataSource(
        remoteStudentsDataSourceImpl: RemoteStudentsDataSourceImpl
    ): RemoteStudentsDataSource

    @Binds
    abstract fun provideRemoteUserDataSource(
        remoteUserDataSourceImpl: RemoteUserDataSourceImpl
    ): RemoteUserDataSource

    @Binds
    abstract fun provideRemoteMealDataSource(
        remoteMealDataSourceImpl: RemoteMealDataSourceImpl
    ): RemoteMealDataSource

    @Binds
    abstract fun provideRemoteNoticeDataSource(
        remoteNoticeDataSourceImpl: RemoteNoticeDataSourceImpl
    ): RemoteNoticeDataSource

    @Binds
    abstract fun provideRemoteSchoolsDataSource(
        remoteSchoolsDataSourceImpl: RemoteSchoolsDataSourceImpl
    ): RemoteSchoolsDataSource

    @Binds
    abstract fun provideRemoteMyPageDataSource(
        remoteMyPageDataSourceImpl: RemoteMyPageDataSourceImpl
    ): RemoteMyPageDataSource

    @Binds
    abstract fun provideRemoteStudyRoomDataSource(
        remoteStudyRoomDataSourceImpl: RemoteStudyRoomDataSourceImpl
    ): RemoteStudyRoomDataSource
}
