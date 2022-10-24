package com.example.auth_data.remote.api

import com.example.auth_data.remote.request.auth.GetEmailCodeRequest
import com.example.auth_data.remote.request.auth.SignInRequest
import com.example.auth_data.remote.response.auth.SignInResponse
import com.example.auth_data.remote.url.DmsUrl
import com.example.auth_domain.enum.EmailType
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthApi {
    @POST(DmsUrl.User.login)
    suspend fun postLogin(
        signInRequest: SignInRequest,
    ): SignInResponse

    @POST(DmsUrl.User.emailCode)
    suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    )

    @GET(DmsUrl.User.emailCode)
    suspend fun checkEmailCode(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
        @Query("type") type: EmailType,
    )

    @PUT(DmsUrl.User.refreshToken)
    suspend fun refreshToken(
        @Header("refresh-token") refreshToken: String,
    )

    @GET(DmsUrl.User.compareEmail)
    suspend fun compareEmail(
        @Query("account_id") accountId: String,
        @Query("email") email: String,
    )

    @GET(DmsUrl.User.checkId)
    suspend fun checkId(
        @Query("account_id") accountId: String,
    )
}