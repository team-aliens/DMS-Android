package com.example.auth_domain.param

import java.util.UUID

data class SchoolAnswerParam(
    val schoolId: UUID,
    val answer: String,
)
