package team.aliens.data.repository

import kotlinx.coroutines.flow.Flow
import team.aliens.data.remote.datasource.declaration.RemoteSchoolsDataSource
import team.aliens.data.remote.response.schools.SchoolConfirmQuestionResponse
import team.aliens.data.remote.response.schools.SchoolIdResponse
import team.aliens.data.util.OfflineCacheUtil
import team.aliens.domain.entity.schools.SchoolEntity
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity
import team.aliens.domain.entity.user.SchoolIdEntity
import team.aliens.domain.param.SchoolAnswerParam
import team.aliens.domain.repository.SchoolsRepository
import java.util.*
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val remoteSchoolsDataSource: RemoteSchoolsDataSource,
) : SchoolsRepository {

    override suspend fun schoolQuestion(schoolId: UUID): Flow<SchoolConfirmQuestionEntity> =
        OfflineCacheUtil<SchoolConfirmQuestionEntity>().remoteData {
            remoteSchoolsDataSource.schoolQuestion(schoolId).toEntity()
        }.createRemoteFlow()

    override suspend fun compareSchoolAnswer(schoolAnswerParam: SchoolAnswerParam) {
        remoteSchoolsDataSource.compareSchoolAnswer(
            schoolId = schoolAnswerParam.schoolId,
            answer = schoolAnswerParam.answer,
        )
    }

    override suspend fun examineSchoolCode(schoolCode: String) =
        remoteSchoolsDataSource.examineSchoolCode(schoolCode).toEntity()

    override suspend fun fetchSchools(): List<SchoolEntity> {
        return remoteSchoolsDataSource.fetchSchools()
    }

    private fun SchoolConfirmQuestionResponse.toEntity() =
        SchoolConfirmQuestionEntity(question = question)

    private fun SchoolIdResponse.toEntity() = SchoolIdEntity(schoolId = schoolId)
}