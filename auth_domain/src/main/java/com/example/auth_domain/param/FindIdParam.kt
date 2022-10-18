package com.example.auth_domain.param

import java.util.UUID

data class FindIdParam(
    val schoolId: UUID,
    val answer: String,
)
