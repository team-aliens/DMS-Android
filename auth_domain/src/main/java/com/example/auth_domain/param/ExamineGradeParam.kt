package com.example.auth_domain.param

import java.util.UUID

data class ExamineGradeParam(
    val schoolId: UUID,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
)