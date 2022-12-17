package com.example.domain.param

import com.example.domain.enums.EmailType

data class RequestEmailCodeParam(
    val email: String,
    val type: EmailType,
)
