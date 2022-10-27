package com.example.auth_domain.usecase.students

import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class duplicateCheckIdUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studentsRepository.duplicateCheckId(data)
    }
}