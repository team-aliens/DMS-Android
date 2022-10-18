package com.example.auth_domain.repository

import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.param.FindIdParam

interface StudentsRepository {

    suspend fun findId (
        findIdParam: FindIdParam,
    )

    suspend fun changePassword (
        changePasswordParam: ChangePasswordParam,
    )
}
