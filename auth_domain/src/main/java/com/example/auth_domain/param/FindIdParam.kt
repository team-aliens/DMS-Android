package com.example.auth_domain.param

import java.util.UUID

data class FindIdParam(
    val schoolId: UUID,
    val name: String,
    val grade: Int,
    val class_room: Int,
    val number: Int,
)
