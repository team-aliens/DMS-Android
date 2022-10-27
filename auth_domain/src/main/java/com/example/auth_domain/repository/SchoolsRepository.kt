package com.example.auth_domain.repository

import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.auth_domain.entity.SchoolIdEntity
import com.example.auth_domain.param.SchoolAnswerParam
import java.util.UUID

interface SchoolsRepository {

    suspend fun schoolQuestion(
        schoolId: UUID
    ): SchoolConfirmQuestionEntity

    suspend fun compareSchoolAnswer(
        schoolAnswerParam: SchoolAnswerParam
    )

    suspend fun examineSchoolCode(
        schoolCode: String
    ): SchoolIdEntity
}