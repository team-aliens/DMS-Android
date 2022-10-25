package com.example.auth_domain.param

data class ChangePasswordParam(
    val accountId: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
)
