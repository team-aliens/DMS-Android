package com.example.domain.usecase.students

import com.example.domain.param.ResetPasswordParam
import com.example.domain.repository.StudentsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteResetPasswordUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<ResetPasswordParam, Unit>() {
    override suspend fun execute(data: ResetPasswordParam) {
        studentsRepository.resetPassword(data)
    }
}