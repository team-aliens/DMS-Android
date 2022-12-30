package com.example.domain.repository

import com.example.domain.entity.user.AuthInfoEntity
import com.example.domain.param.CheckEmailCodeParam
import com.example.domain.param.CompareEmailParam
import com.example.domain.param.LoginParam
import com.example.domain.param.RequestEmailCodeParam
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun userSignIn(
        loginParam: LoginParam,
    )

    suspend fun autoSignIn()

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
