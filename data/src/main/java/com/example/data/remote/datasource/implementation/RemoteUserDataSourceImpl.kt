package com.example.data.remote.datasource.implementation

import com.example.data.remote.request.user.GetEmailCodeRequest
import com.example.data.remote.request.user.SignInRequest
import com.example.data.remote.response.user.SignInResponse
import com.example.data.remote.api.UserApi
import com.example.data.remote.datasource.declaration.RemoteUserDataSource
import com.example.data.util.HttpHandler
import com.example.domain.enums.EmailType
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {

    override suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ) = HttpHandler<SignInResponse>()
        .httpRequest {
            userApi.postLogin(
                signInRequest,
            )
        }
        .sendRequest()

    override suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.requestEmailCode(
                requestEmailCodeRequest,
            )
        }
        .sendRequest()

    override suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.checkEmailCode(
                email,
                authCode,
                type,
            )
        }
        .sendRequest()

    override suspend fun refreshToken(
        refreshToken: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.refreshToken(
                refreshToken,
            )
        }
        .sendRequest()

    override suspend fun compareEmail(
        accountId: String,
        email: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.compareEmail(
                accountId,
                email,
            )
        }
        .sendRequest()

    override suspend fun checkId(
        accountId: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.checkId(
                accountId,
            )
        }
        .sendRequest()
}
