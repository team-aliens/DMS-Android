package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.StudentsApi
import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.students.SignUpResponse
import com.example.auth_data.util.HttpHandler
import javax.inject.Inject

class RemoteStudentsDataSourceImpl @Inject constructor(
    private val studentsApi: StudentsApi,
) : RemoteStudentsDataSource {
    override suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    ) = HttpHandler<SignUpResponse>()
        .httpRequest {
            studentsApi.postRegister(
                signUpRequest,
            )
        }.sendRequest()

    override suspend fun duplicateCheckId(
        accountId: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentsApi.duplicateCheckId(
                accountId
            )
        }.sendRequest()

    override suspend fun duplicateCheckEmail(
        email: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentsApi.duplicateCheckEmail(
                email
            )
        }.sendRequest()
}