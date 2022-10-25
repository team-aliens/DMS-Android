package com.example.auth_domain.usecase.auth

import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class duplicateCheckEmailUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studentsRepository.duplicateCheckEmail(data)
    }
}