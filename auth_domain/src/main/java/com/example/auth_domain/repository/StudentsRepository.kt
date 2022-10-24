package com.example.auth_domain.repository

import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.param.ResetPasswordParam

interface StudentsRepository {

    suspend fun register(
        registerParam: RegisterParam,
    )

    suspend fun duplicateCheckId(
        accountId: String
    )

    suspend fun duplicateCheckEmail(
        email: String
    )

    suspend fun resetPassword(
        resetPasswordParam: ResetPasswordParam
    )
}