package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.SignInRequest
import com.example.auth_data.remote.request.SignUpRequest
import com.example.auth_data.remote.response.SignInResponse

interface RemoteUserDataSource {

    suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ): SignInResponse

    suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    )
}
