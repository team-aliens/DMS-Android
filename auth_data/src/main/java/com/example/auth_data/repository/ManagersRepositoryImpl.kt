package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteManagersDataSource
import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.param.FindIdParam
import com.example.auth_domain.repository.ManagersRepository
import javax.inject.Inject

class ManagersRepositoryImpl @Inject constructor(
    private val remoteManagersDataSource: RemoteManagersDataSource,
) : ManagersRepository {
    override suspend fun findId(
        findIdParam: FindIdParam
    ) {
        remoteManagersDataSource.findId(
            schoolId = findIdParam.schoolId,
            answer = findIdParam.answer,
        )
    }

    override suspend fun changePassword(
        changePasswordParam: ChangePasswordParam,
    ) {
        remoteManagersDataSource.changePassword(
            changePasswordParam.toRequest()
        )
    }
}

private fun ChangePasswordParam.toRequest() =
    ChangePasswordRequest(
        accountId = accountId,
        email = email,
        authCode = authCode,
        newPassword = newPassword,
    )
