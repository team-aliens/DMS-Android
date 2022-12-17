package com.example.domain.param

data class RegisterParam(
    val schoolCode: String,
    val schoolAnswer: String,
    val email: String,
    val authCode: String,
    val classRoom : Int,
    val grade: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val profileImageUrl: String?,
)
