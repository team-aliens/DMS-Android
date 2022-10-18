package com.example.auth_data.remote.datasource.implementation

import com.example.auth_data.remote.api.ManagersApi
import com.example.auth_data.remote.datasource.declaration.RemoteManagersDataSource
import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_data.remote.response.IdResponse
import com.example.auth_data.util.HttpHandler
import java.util.UUID
import javax.inject.Inject

class RemoteManagersDataSourceImpl @Inject constructor(
    private val managersApi: ManagersApi,
): RemoteManagersDataSource {

    override suspend fun findId(
        schoolId: UUID,
        answer: String,
    ) = HttpHandler<IdResponse>()
        .httpRequest { managersApi.findId(
            schoolId,
            answer,
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
