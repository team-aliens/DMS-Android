package com.example.auth_domain.repository

import com.example.auth_domain.param.LoginParam

interface UserRepository {
    suspend fun login(loginParam: LoginParam)
}