package com.example.data.repository

import com.example.data.remote.request.user.GetEmailCodeRequest
import com.example.data.remote.request.user.SignInRequest
import com.example.data.remote.response.user.SignInResponse
import com.example.data.remote.datasource.declaration.RemoteUserDataSource
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.user.AuthInfoEntity
import com.example.domain.param.CheckEmailCodeParam
import com.example.domain.param.CompareEmailParam
import com.example.domain.param.LoginParam
import com.example.domain.param.RequestEmailCodeParam
import com.example.domain.repository.UserRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {

    override suspend fun login(loginParam: LoginParam): Flow<AuthInfoEntity> =
        OfflineCacheUtil<AuthInfoEntity>()
            .remoteData { remoteUserDataSource.postUserSignIn(loginParam.toRequest()).toEntity() }
            .doOnNeedRefresh {
                val response = remoteUserDataSource.postUserSignIn(
                    loginParam.toRequest()
                )

                localUserDataSource.setPersonalKey(response.toDbEntity())
                localUserDataSource.setUserVisibleInform(response.features.toDbEntity())
            }
            .createFlow()

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
            accessTokenExpiredAt = accessTokenExpiredAt,
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt,
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
            accessTokenExpiredAt = accessTokenExpiredAt,
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt,
            features = features.toEntity()
        )

    private fun SignInResponse.Features.toEntity() =
        AuthInfoEntity.Features(
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
