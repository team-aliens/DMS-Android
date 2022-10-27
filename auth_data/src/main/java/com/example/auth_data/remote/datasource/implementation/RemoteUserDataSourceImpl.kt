package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.UserApi
import com.example.auth_data.remote.datasource.declaration.RemoteUserDataSource
import com.example.auth_data.remote.request.user.GetEmailCodeRequest
import com.example.auth_data.remote.request.user.SignInRequest
import com.example.auth_data.remote.response.user.SignInResponse
import com.example.auth_data.util.HttpHandler
import com.example.auth_domain.enum.EmailType
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
): RemoteUserDataSource {

    override suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ) = HttpHandler<SignInResponse>()
        .httpRequest { userApi.postLogin(
            signInRequest,
        ) }
        .sendRequest()

    override suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.requestEmailCode(
            requestEmailCodeRequest,
        ) }
        .sendRequest()

    override suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.checkEmailCode(
            email,
            authCode,
            type,
        ) }
        .sendRequest()

    override suspend fun refreshToken(
        refreshToken: String,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.refreshToken(
            refreshToken,
        ) }
        .sendRequest()

    override suspend fun compareEmail(
        accountId: String,
        email: String,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.compareEmail(
            accountId,
            email,
        ) }
        .sendRequest()

    override suspend fun checkId(
        accountId: String,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.checkId(
            accountId,
        ) }
        .sendRequest()
}
