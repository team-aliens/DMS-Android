package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.auth.GetEmailCodeRequest
import com.example.auth_data.remote.request.auth.SignInRequest
import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.auth.SignInResponse
import com.example.auth_domain.enum.EmailType

interface RemoteAuthDataSource {

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
