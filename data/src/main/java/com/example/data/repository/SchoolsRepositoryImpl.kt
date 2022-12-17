package com.example.data.repository

import com.example.auth_data.remote.response.schools.SchoolConfirmQuestionResponse
import com.example.auth_data.remote.response.schools.SchoolIdResponse
import com.example.data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.domain.entity.user.SchoolConfirmQuestionEntity
import com.example.domain.entity.user.SchoolIdEntity
import com.example.domain.param.SchoolAnswerParam
import com.example.domain.repository.SchoolsRepository
import java.util.UUID
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val remoteSchoolsDataSource: RemoteSchoolsDataSource
) : SchoolsRepository {

    override suspend fun schoolQuestion(schoolId: UUID): SchoolConfirmQuestionEntity =
        remoteSchoolsDataSource.schoolQuestion(schoolId).toEntity()

    override suspend fun compareSchoolAnswer(schoolAnswerParam: SchoolAnswerParam) {
        remoteSchoolsDataSource.compareSchoolAnswer(
            schoolId = schoolAnswerParam.schoolId,
            answer = schoolAnswerParam.answer,
        )
    }

    override suspend fun examineSchoolCode(schoolCode: String) =
        remoteSchoolsDataSource.examineSchoolCode(schoolCode).toEntity()

    private fun SchoolConfirmQuestionResponse.toEntity() =
        SchoolConfirmQuestionEntity(
            question = question
        )

    private fun SchoolIdResponse.toEntity() =
        SchoolIdEntity(
            schoolId = schoolId
        )
}