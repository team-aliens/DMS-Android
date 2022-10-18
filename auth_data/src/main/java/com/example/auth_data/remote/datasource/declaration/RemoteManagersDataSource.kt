package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_data.remote.response.IdResponse
import java.util.UUID

interface RemoteManagersDataSource {

    suspend fun findId(
        schoolId: UUID,
        answer: String,
    ): IdResponse

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
    )
}
