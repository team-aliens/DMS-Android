package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.ManagersApi
import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_data.remote.response.IdResponse
import com.example.auth_data.util.HttpHandler
import java.util.UUID
import javax.inject.Inject

class RemoteStudentsDataSourceImpl @Inject constructor(
    private val managersApi: ManagersApi,
): RemoteStudentsDataSource {

    override suspend fun findId(
        schoolId: UUID,
        name: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ) = HttpHandler<IdResponse>()
        .httpRequest { managersApi.findId(
            schoolId,
            name,
            grade,
            classRoom,
            number,
        ) }
        .sendRequest()

    override suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
    ) = HttpHandler<Unit>()
        .httpRequest { managersApi.changePassword(
            changePasswordRequest,
        ) }
        .sendRequest()
}
