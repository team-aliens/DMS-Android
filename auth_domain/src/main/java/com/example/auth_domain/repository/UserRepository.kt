package com.example.auth_domain.repository

import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.param.RegisterParam

interface UserRepository {

    suspend fun login(loginParam: LoginParam)

    suspend fun register(registerParam: RegisterParam)

}
