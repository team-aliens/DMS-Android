package com.example.auth_data.remote.api

import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.students.SignUpResponse
import com.example.auth_data.remote.url.DmsUrl
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudentsApi {
    @POST(DmsUrl.Students.register)
    suspend fun postRegister(
        signUpRequest: SignUpRequest,
    ) : SignUpResponse

    @GET(DmsUrl.Students.duplicateCheckId)
    suspend fun duplicateCheckId(
        @Query("account_id") accountId: String
    )

    @GET(DmsUrl.Students.duplicateCheckEmail)
    suspend fun duplicateCheckEmail(
        @Query("email") email: String
    )
}