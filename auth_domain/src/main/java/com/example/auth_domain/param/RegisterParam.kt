package com.example.auth_domain.param

data class RegisterParam(
    val schoolCode: String,
    val schoolAnswer: String,
    val email: String,
    val authCode: String,
    val grade: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val profileImageUrl: String?,
)
