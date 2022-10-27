package com.example.auth_domain.param

import com.example.auth_domain.enum.EmailType

data class CheckEmailCodeParam(
    val email: String,
    val authCode: String,
    val type: EmailType,
)