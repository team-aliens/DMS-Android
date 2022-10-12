package com.example.auth_data.remote.api

import com.example.auth_data.remote.request.SignInRequest
import com.example.auth_data.remote.request.SignUpRequest
import com.example.auth_data.remote.response.SignInResponse
import com.example.auth_data.remote.url.DmsUrl
import retrofit2.http.POST

interface UserApi {

    @POST (DmsUrl.User.login)
    suspend fun postLogin(
        signInRequest: SignInRequest,
    ): SignInResponse

    @POST (DmsUrl.User.register)
    suspend fun postRegister(
        signUpRequest: SignUpRequest,
    )
}