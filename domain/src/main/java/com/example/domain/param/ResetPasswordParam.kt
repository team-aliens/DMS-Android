package com.example.domain.param

class ResetPasswordParam(
    val accountId: String,
    val authCode: String,
    val email: String,
    val name: String,
    val newPassword: String
)