package com.example.domain.repository

import com.example.domain.entity.user.ExamineGradeEntity
import com.example.domain.param.ExamineGradeParam
import com.example.domain.param.RegisterParam
import com.example.domain.param.ResetPasswordParam

interface StudentsRepository {

    suspend fun register(
        registerParam: RegisterParam
    )

    suspend fun duplicateCheckId(
        accountId: String
    )

    suspend fun duplicateCheckEmail(
        email: String
    )

    suspend fun resetPassword(
        resetPasswordParam: ResetPasswordParam
    )

    suspend fun examineGrade(
        examineGradeParam: ExamineGradeParam
    ): ExamineGradeEntity
}