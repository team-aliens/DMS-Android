package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.RequestEmailCodeRequest
import com.example.auth_data.remote.request.SignInRequest
import com.example.auth_data.remote.request.SignUpRequest
import com.example.auth_data.remote.response.SignInResponse
import com.example.auth_domain.enum.EmailType

interface RemoteUserDataSource {

    suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ): SignInResponse

    suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    )

    suspend fun requestEmailCode(
        requestEmailCodeRequest: RequestEmailCodeRequest,
    )

    suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    )

    suspend fun refreshToken(
        refreshToken: String,
    )

    suspend fun compareEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkId(
        accountId: String,
    )
}
