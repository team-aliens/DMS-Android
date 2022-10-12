package com.example.auth_domain.param

data class RegisterParam(
    val name: String,
    val accountId: String,
    val password: String,
    val profileImageUrl: String?,
)
