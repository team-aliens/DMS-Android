package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.SchoolsApi
import team.aliens.data.remote.datasource.declaration.RemoteSchoolsDataSource
import team.aliens.data.remote.response.schools.SchoolConfirmQuestionResponse
import team.aliens.data.remote.response.schools.SchoolIdResponse
import team.aliens.data.util.HttpHandler
import java.util.UUID
import javax.inject.Inject

class RemoteSchoolsDataSourceImpl @Inject constructor(
    private val schoolsApi: SchoolsApi
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
}