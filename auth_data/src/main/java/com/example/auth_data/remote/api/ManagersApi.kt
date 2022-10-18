package com.example.auth_data.remote.api

import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_data.remote.response.IdResponse
import com.example.auth_data.remote.url.DmsUrl
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface ManagersApi {

    @GET (DmsUrl.Managers.findId)
    suspend fun findId (
        @Path ("school-id") schoolId: UUID,
        @Query ("answer") answer: String,
    ): IdResponse

    @PATCH (DmsUrl.Managers.changePassword)
    suspend fun changePassword (
        @Body changePasswordRequest: ChangePasswordRequest,
    )
}
