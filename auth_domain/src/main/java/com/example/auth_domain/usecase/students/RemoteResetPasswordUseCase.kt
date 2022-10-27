package com.example.auth_domain.usecase.students

import com.example.auth_domain.param.ResetPasswordParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteResetPasswordUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<ResetPasswordParam, Unit>() {
    override suspend fun execute(data: ResetPasswordParam) {
        studentsRepository.resetPassword(data)
    }
}