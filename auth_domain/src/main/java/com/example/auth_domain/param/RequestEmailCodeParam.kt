package com.example.auth_domain.param

import com.example.auth_domain.enum.EmailType

data class RequestEmailCodeParam(
    val email: String,
    val type: EmailType,
)
