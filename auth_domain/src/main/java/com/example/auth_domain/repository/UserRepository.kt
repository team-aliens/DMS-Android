package com.example.auth_domain.repository

import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.param.ResetPasswordParam

interface UserRepository {

    suspend fun login(
        loginParam: LoginParam,
    )

    suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam
    )

    suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    )

    suspend fun refreshToken(
        refreshToken: String,
    )

    suspend fun compareEmail(
        compareEmailParam: CompareEmailParam
    )

    suspend fun checkId(
        accountId: String,
    )
}
