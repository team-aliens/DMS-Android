package com.example.data.repository

import com.example.data.remote.response.schools.SchoolConfirmQuestionResponse
import com.example.data.remote.response.schools.SchoolIdResponse
import com.example.data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.user.SchoolConfirmQuestionEntity
import com.example.domain.entity.user.SchoolIdEntity
import com.example.domain.param.SchoolAnswerParam
import com.example.domain.repository.SchoolsRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val remoteSchoolsDataSource: RemoteSchoolsDataSource
) : SchoolsRepository {

    override suspend fun schoolQuestion(schoolId: UUID): Flow<SchoolConfirmQuestionEntity> =
        OfflineCacheUtil<SchoolConfirmQuestionEntity>()
            .remoteData { remoteSchoolsDataSource.schoolQuestion(schoolId).toEntity() }
            .createRemoteFlow()

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