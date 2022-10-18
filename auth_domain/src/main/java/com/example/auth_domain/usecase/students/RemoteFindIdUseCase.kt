package com.example.auth_domain.usecase.students

import com.example.auth_domain.param.FindIdParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteFindIdUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
): UseCase<FindIdParam, Unit>() {
    override suspend fun execute(data: FindIdParam) {
        studentsRepository.findId(data)
    }
}
