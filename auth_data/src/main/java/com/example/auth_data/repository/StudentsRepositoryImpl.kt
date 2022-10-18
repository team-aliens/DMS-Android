package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.request.ChangePasswordRequest
import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.param.FindIdParam
import com.example.auth_domain.repository.StudentsRepository
import javax.inject.Inject

class ManagersRepositoryImpl @Inject constructor(
    private val remoteStudentsDataSource: RemoteStudentsDataSource,
) : StudentsRepository {

    override suspend fun findId(
        findIdParam: FindIdParam,
    ) {
        remoteStudentsDataSource.findId(
            schoolId = findIdParam.schoolId,
            name = findIdParam.name,
            grade = findIdParam.grade,
            classRoom = findIdParam.class_room,
            number = findIdParam.number,
        )
    }

    override suspend fun changePassword(
        changePasswordParam: ChangePasswordParam,
    ) {
        remoteStudentsDataSource.changePassword(
            changePasswordParam.toRequest(),
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
