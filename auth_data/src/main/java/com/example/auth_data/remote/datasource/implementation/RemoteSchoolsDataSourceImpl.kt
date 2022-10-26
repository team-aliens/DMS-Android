package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.SchoolsApi
import com.example.auth_data.remote.datasource.declaration.RemoteSchoolsDataSource
import com.example.auth_data.remote.response.schools.SchoolConfirmQuestionResponse
import com.example.auth_data.remote.response.schools.SchoolIdResponse
import com.example.auth_data.util.HttpHandler
import java.util.UUID
import javax.inject.Inject

class RemoteSchoolsDataSourceImpl @Inject constructor(
    private val schoolsApi: SchoolsApi
) : RemoteSchoolsDataSource {

    override suspend fun schoolQuestion(schoolId: UUID) =
        HttpHandler<SchoolConfirmQuestionResponse>()
            .httpRequest { schoolsApi.schoolQuestion(schoolId) }
            .sendRequest()

    override suspend fun schoolAnswer(schoolId: UUID, answer: String) =
        HttpHandler<Unit>()
            .httpRequest {
                schoolsApi.schoolAnswer(
                    schoolId = schoolId,
                    answer = answer,
                )
            }
            .sendRequest()

    override suspend fun schoolCode(schoolCode: String) =
        HttpHandler<SchoolIdResponse>()
            .httpRequest { schoolsApi.schoolCode(schoolCode) }
            .sendRequest()
}