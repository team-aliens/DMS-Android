package team.aliens.data.remote.datasource.implementation

import team.aliens.data.extension.toDomain
import team.aliens.data.remote.api.SchoolsApi
import team.aliens.data.remote.datasource.declaration.RemoteSchoolsDataSource
import team.aliens.data.remote.response.schools.SchoolConfirmQuestionResponse
import team.aliens.data.remote.response.schools.SchoolIdResponse
import team.aliens.data.util.HttpHandler
import team.aliens.data.util.sendHttpRequest
import team.aliens.domain.entity.schools.SchoolEntity
import java.util.*
import javax.inject.Inject

class RemoteSchoolsDataSourceImpl @Inject constructor(
    private val schoolsApi: SchoolsApi,
) : RemoteSchoolsDataSource {

    override suspend fun schoolQuestion(schoolId: UUID) =
        HttpHandler<SchoolConfirmQuestionResponse>()
            .httpRequest { schoolsApi.schoolQuestion(schoolId) }
            .sendRequest()

    override suspend fun compareSchoolAnswer(schoolId: UUID, answer: String) =
        HttpHandler<Unit>()
            .httpRequest {
                schoolsApi.compareSchoolAnswer(
                    schoolId = schoolId,
                    answer = answer,
                )
            }
            .sendRequest()

    override suspend fun examineSchoolCode(schoolCode: String) =
        HttpHandler<SchoolIdResponse>()
            .httpRequest { schoolsApi.examineSchoolCode(schoolCode) }
            .sendRequest()

    override suspend fun fetchSchools(): List<SchoolEntity> {
        return sendHttpRequest(
            httpRequest = {
                schoolsApi.fetchSchools()
            },
        ).toDomain()
    }
}