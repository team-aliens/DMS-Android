package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_data.remote.response.IdResponse
import java.util.UUID

interface RemoteStudentsDataSource {

    suspend fun findId(
        schoolId: UUID,
        name: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): IdResponse

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
    )
}
