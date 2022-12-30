package com.example.domain.param

data class LoginParam(
    val id : String,
    val password: String,
    val autoLogin: Boolean,
)
