package com.example.auth_domain.repository

import com.example.auth_domain.enum.EmailType
import com.example.auth_domain.param.*

interface UserRepository {

    suspend fun login(
        loginParam: LoginParam,
    )

    suspend fun register(
        registerParam: RegisterParam,
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
