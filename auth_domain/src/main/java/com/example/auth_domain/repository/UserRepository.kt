package com.example.auth_domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.auth_domain.entity.AuthInfoEntity
import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.param.ResetPasswordParam
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(
        loginParam: LoginParam,
    ): Flow<AuthInfoEntity>

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
