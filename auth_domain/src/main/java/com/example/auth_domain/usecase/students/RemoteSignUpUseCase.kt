package com.example.auth_domain.usecase.students

import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteSignUpUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
): UseCase<RegisterParam, Unit>() {
    override suspend fun execute(data: RegisterParam) {
        studentsRepository.register(data)
    }
}
