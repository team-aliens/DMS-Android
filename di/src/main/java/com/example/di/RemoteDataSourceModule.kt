package com.example.di

import com.example.auth_data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.datasource.declaration.RemoteUserDataSource
import com.example.auth_data.remote.datasource.implementation.RemoteSchoolsDataSourceImpl
import com.example.auth_data.remote.datasource.implementation.RemoteStudentsDataSourceImpl
import com.example.auth_data.remote.datasource.implementation.RemoteUserDataSourceImpl
import com.example.feature_data.remote.datasource.declaration.RemoteMealDataSource
import com.example.feature_data.remote.datasource.implementation.RemoteMealDataSourceImpl
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
    abstract fun provideRemoteSchoolsDataSource(
        remoteSchoolsDataSourceImpl: RemoteSchoolsDataSourceImpl
    ): RemoteSchoolsDataSource
}