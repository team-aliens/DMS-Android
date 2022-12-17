package com.example.domain.param

import com.example.domain.enums.EmailType

data class CheckEmailCodeParam(
    val email: String,
    val authCode: String,
    val type: EmailType,
)