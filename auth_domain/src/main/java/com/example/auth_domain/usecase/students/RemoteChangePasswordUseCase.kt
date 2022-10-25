package com.example.auth_domain.usecase.students

import com.example.auth_domain.param.ChangePasswordParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteChangePasswordUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
): UseCase<ChangePasswordParam, Unit>() {
    override suspend fun execute(data: ChangePasswordParam) {
        studentsRepository.changePassword(data)
    }
}
