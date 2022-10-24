package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.AuthApi
import com.example.auth_data.remote.datasource.declaration.RemoteAuthDataSource
import com.example.auth_data.remote.request.auth.GetEmailCodeRequest
import com.example.auth_data.remote.request.auth.SignInRequest
import com.example.auth_data.remote.response.auth.SignInResponse
import com.example.auth_data.util.HttpHandler
import com.example.auth_domain.enum.EmailType
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
): RemoteAuthDataSource {

    override suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ) = HttpHandler<SignInResponse>()
        .httpRequest { authApi.postLogin(
            signInRequest,
        ) }
        .sendRequest()

    override suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    ) = HttpHandler<Unit>()
        .httpRequest { authApi.requestEmailCode(
            requestEmailCodeRequest,
        ) }
        .sendRequest()

    override suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    ) = HttpHandler<Unit>()
        .httpRequest { authApi.checkEmailCode(
            email,
            authCode,
            type,
        ) }
        .sendRequest()

    override suspend fun refreshToken(
        refreshToken: String,
    ) = HttpHandler<Unit>()
        .httpRequest { authApi.refreshToken(
            refreshToken,
        ) }
        .sendRequest()

    override suspend fun compareEmail(
        accountId: String,
        email: String,
    ) = HttpHandler<Unit>()
        .httpRequest { authApi.compareEmail(
            accountId,
            email,
        ) }
        .sendRequest()

    override suspend fun checkId(
        accountId: String,
    ) = HttpHandler<Unit>()
        .httpRequest { authApi.checkId(
            accountId,
        ) }
        .sendRequest()
}
