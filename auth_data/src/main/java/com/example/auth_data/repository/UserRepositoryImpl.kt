package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteUserDataSource
import com.example.auth_data.remote.request.SignInRequest
import com.example.auth_data.remote.response.SignInResponse
import com.example.auth_domain.entity.UserVisibleEntity
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.repository.UserRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam
import javax.inject.Inject
import kotlin.math.log

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
): UserRepository {

    override suspend fun login(
        loginParam: LoginParam
    ) {
        val response = remoteUserDataSource.postUserSignIn(
            SignInRequest(
                id = loginParam.id,
                password = loginParam.password,
            )
        )

        localUserDataSource.setUserVisibleInform(response.toEntity())
    }

    private fun SignInResponse.toEntity() = UserVisibleParam(
        surveyBoolean = surveyBoolean,
        noticeBoolean = noticeBoolean,
        myPageBoolean = myPageBoolean,
        recentRoomBoolean = recentBoolean,
    )
}