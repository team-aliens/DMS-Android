package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteAuthDataSource
import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.request.auth.GetEmailCodeRequest
import com.example.auth_data.remote.request.auth.SignInRequest
import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.auth.SignInResponse
import com.example.auth_data.remote.response.students.SignUpResponse
import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.repository.UserRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val remoteStudentsDataSource: RemoteStudentsDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {

    override suspend fun login(
        loginParam: LoginParam,
    ) {
        val response = remoteAuthDataSource.postUserSignIn(
            loginParam.toRequest()
        )

        localUserDataSource.setUserVisibleInform(response.features.toEntity())
    }

    override suspend fun register(
        registerParam: RegisterParam,
    ) {
        val response = remoteStudentsDataSource.postUserSignUp(
            registerParam.toRequest()
        )
        localUserDataSource.setUserVisibleInform(response.features.toEntity())
    }

    override suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam,
    ) = remoteAuthDataSource.requestEmailCode(requestEmailCodeParam.toRequest())

    override suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    ) = remoteAuthDataSource.checkEmailCode(
        checkEmailCodeParam.email,
        checkEmailCodeParam.accountId,
        checkEmailCodeParam.type
    )

    override suspend fun refreshToken(
        refreshToken: String,
    ) = remoteAuthDataSource.refreshToken(refreshToken)

    override suspend fun compareEmail(
        compareEmailParam: CompareEmailParam,
    ) = remoteAuthDataSource.compareEmail(
        compareEmailParam.accountId,
        compareEmailParam.email,
    )

    override suspend fun checkId(
        accountId: String,
    ) = remoteAuthDataSource.checkId(accountId)

    override suspend fun duplicateCheckId(
        accountId: String,
    ) = remoteStudentsDataSource.duplicateCheckId(accountId)

    override suspend fun duplicateCheckEmail(
        email: String,
    ) = remoteStudentsDataSource.duplicateCheckEmail(email)
}

private fun SignInResponse.toEntity() =
    UserVisibleParam(
        accessToken = accessToken,
        expiredAt = expiredAt,
        refreshToken = refreshToken,
        features = features.toEntity()
    )

private fun SignInResponse.Features.toEntity() =
    UserVisibleParam.FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )

private fun SignUpResponse.toEntity() =
    UserVisibleParam(
        accessToken = accessToken,
        expiredAt = expiredAt,
        refreshToken = refreshToken,
        features = features.toEntity()
    )

private fun SignUpResponse.Features.toEntity() =
    UserVisibleParam.FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )

private fun LoginParam.toRequest() =
    SignInRequest(
        id = id,
        password = password,
    )

private fun RegisterParam.toRequest() =
    SignUpRequest(
        schoolCode = schoolCode,
        schoolAnswer = schoolAnswer,
        email = email,
        authCode = authCode,
        grade = grade,
        number = number,
        accountId = accountId,
        password = password,
        profileImageUrl = profileImageUrl,
    )

private fun RequestEmailCodeParam.toRequest() =
    GetEmailCodeRequest(
        email = email,
        type = type,
    )
