package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.auth_domain.param.SchoolAnswerParam
import com.example.auth_domain.repository.SchoolsRepository
import java.util.UUID
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val remoteSchoolsDataSource: RemoteSchoolsDataSource
) : SchoolsRepository {

    override suspend fun schoolQuestion(schoolId: UUID): SchoolConfirmQuestionEntity =
        remoteSchoolsDataSource.schoolQuestion(schoolId)

    override suspend fun schoolAnswer(schoolAnswerParam: SchoolAnswerParam) {
        remoteSchoolsDataSource.schoolAnswer(
            schoolId = schoolAnswerParam.schoolId,
            answer = schoolAnswerParam.answer,
        )
    }

    override suspend fun schoolCode(schoolCode: String) =
        remoteSchoolsDataSource.schoolCode(schoolCode)
}