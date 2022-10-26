package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteUserDataSource
import com.example.auth_data.remote.request.user.GetEmailCodeRequest
import com.example.auth_data.remote.request.user.SignInRequest
import com.example.auth_data.remote.response.user.SignInResponse
import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.repository.UserRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {

    override suspend fun login(
        loginParam: LoginParam,
    ) {
        val response = remoteUserDataSource.postUserSignIn(
            loginParam.toRequest()
        )

        localUserDataSource.setPersonalKey(response.toEntity())
        localUserDataSource.setUserVisibleInform(response.features.toEntity())
    }

    override suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam,
    ) = remoteUserDataSource.requestEmailCode(requestEmailCodeParam.toRequest())

    override suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    ) = remoteUserDataSource.checkEmailCode(
        checkEmailCodeParam.email,
        checkEmailCodeParam.accountId,
        checkEmailCodeParam.type
    )

    override suspend fun refreshToken(
        refreshToken: String,
    ) = remoteUserDataSource.refreshToken(refreshToken)

    override suspend fun compareEmail(
        compareEmailParam: CompareEmailParam,
    ) = remoteUserDataSource.compareEmail(
        compareEmailParam.accountId,
        compareEmailParam.email,
    )

    override suspend fun checkId(
        accountId: String,
    ) = remoteUserDataSource.checkId(accountId)

    private fun SignInResponse.toEntity() =
        UserPersonalKeyParam(
            accessToken = accessToken,
            expiredAt = expiredAt,
            refreshToken = refreshToken,
    )

    private fun SignInResponse.Features.toEntity() =
        FeaturesParam(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
        )

    private fun LoginParam.toRequest() =
        SignInRequest(
            id = id,
            password = password,
        )

    private fun RequestEmailCodeParam.toRequest() =
        GetEmailCodeRequest(
            email = email,
            type = type,
        )
}
