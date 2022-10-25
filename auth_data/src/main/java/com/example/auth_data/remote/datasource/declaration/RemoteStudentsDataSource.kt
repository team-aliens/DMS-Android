package com.example.auth_data.remote.datasource.declaration

import com.example.auth_data.remote.request.students.ResetPasswordRequest
import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.students.SignUpResponse

interface RemoteStudentsDataSource {
    suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    ): SignUpResponse

    suspend fun duplicateCheckId(
        accountId: String
    )

    suspend fun duplicateCheckEmail(
        email: String
    )

    suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest
    )
}