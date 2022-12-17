package com.example.domain.repository

import com.example.domain.entity.user.SchoolConfirmQuestionEntity
import com.example.domain.entity.user.SchoolIdEntity
import com.example.domain.param.SchoolAnswerParam
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