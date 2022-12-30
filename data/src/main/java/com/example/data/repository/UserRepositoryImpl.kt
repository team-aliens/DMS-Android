package com.example.data.repository

import android.util.Log
import com.example.data.remote.request.user.GetEmailCodeRequest
import com.example.data.remote.request.user.SignInRequest
import com.example.data.remote.response.user.SignInResponse
import com.example.data.remote.datasource.declaration.RemoteUserDataSource
import com.example.domain.entity.user.AuthInfoEntity
import com.example.domain.exception.NoInternetException
import com.example.domain.param.CheckEmailCodeParam
import com.example.domain.param.CompareEmailParam
import com.example.domain.param.LoginParam
import com.example.domain.param.RequestEmailCodeParam
import com.example.domain.repository.UserRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.localutil.toLocalDateTime
import com.example.local_database.param.user.UserInfoParam
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {

    override suspend fun userSignIn(loginParam: LoginParam) {
        val response = remoteUserDataSource.postUserSignIn(loginParam.toRequest())
        Log.d("123", loginParam.autoLogin.toString())
        if (loginParam.autoLogin) {
            localUserDataSource.setUserInfo(UserInfoParam(loginParam.id, loginParam.password))
        }
        localUserDataSource.setUserVisibleInform(response.features.toDbEntity())
        localUserDataSource.setPersonalKey(response.toDbEntity())
    }

    override suspend fun autoSignIn() {
        val info = localUserDataSource.fetchUserInfo()
        Log.d("123123", info.toString())
        try {
            val response = remoteUserDataSource.postUserSignIn(signInRequest = SignInRequest(info.id, info.password))
            localUserDataSource.setUserInfo(UserInfoParam(info.id, info.password))
            localUserDataSource.setUserVisibleInform(response.features.toDbEntity())
            localUserDataSource.setPersonalKey(response.toDbEntity())
        } catch (e: NoInternetException) {
            if (info.id.isEmpty() && info.password.isEmpty()) throw e
        }
    }

    override suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam,
    ) = remoteUserDataSource.requestEmailCode(requestEmailCodeParam.toRequest())

    override suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    ) = remoteUserDataSource.checkEmailCode(
        checkEmailCodeParam.email,
        checkEmailCodeParam.authCode,
        checkEmailCodeParam.type,
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

    private fun SignInResponse.toDbEntity() =
        UserPersonalKeyParam(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt.toLocalDateTime(),
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt.toLocalDateTime(),
        )

    private fun SignInResponse.Features.toDbEntity() =
        FeaturesParam(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
        )

    private fun SignInResponse.toEntity() =
        AuthInfoEntity(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt.toLocalDateTime(),
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt.toLocalDateTime(),
            features = features.toEntity()
        )

    private fun SignInResponse.Features.toEntity() =
        AuthInfoEntity.Features(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
        )

    private fun AuthInfoEntity.toDbEntity() =
        UserPersonalKeyParam(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt,
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt,
        )

    private fun AuthInfoEntity.Features.toDbEntity() =
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
