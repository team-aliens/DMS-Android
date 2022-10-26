package com.example.auth_data.remote.datasource.declaration

import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.auth_domain.entity.SchoolIdEntity
import java.util.UUID

interface RemoteSchoolsDataSource {

    suspend fun schoolQuestion(
        schoolId: UUID
    ): SchoolConfirmQuestionEntity

    suspend fun schoolAnswer(
        schoolId: UUID,
        answer: String,
    )

    suspend fun schoolCode(
        schoolCode: String
    ): SchoolIdEntity
}