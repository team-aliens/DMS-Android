package com.example.data.remote.datasource.declaration

import com.example.data.remote.request.user.GetEmailCodeRequest
import com.example.auth_data.remote.request.user.SignInRequest
import com.example.auth_data.remote.response.user.SignInResponse
import com.example.domain.enums.EmailType

interface RemoteUserDataSource {

    suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ): SignInResponse

    suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
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
