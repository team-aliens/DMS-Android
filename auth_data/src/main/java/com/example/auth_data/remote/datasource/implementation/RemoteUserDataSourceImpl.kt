package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.UserApi
import com.example.auth_data.remote.datasource.declaration.RemoteUserDataSource
import com.example.auth_data.remote.request.SignInRequest
import com.example.auth_data.remote.request.SignUpRequest
import com.example.auth_data.remote.response.SignInResponse
import com.example.auth_data.util.HttpHandler
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
): RemoteUserDataSource {

    override suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ) = HttpHandler<SignInResponse>()
        .httpRequest { userApi.postLogin(signInRequest) }
        .sendRequest()

    override suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    ) = HttpHandler<Unit>()
        .httpRequest { userApi.postRegister(signUpRequest) }
        .sendRequest()
}
