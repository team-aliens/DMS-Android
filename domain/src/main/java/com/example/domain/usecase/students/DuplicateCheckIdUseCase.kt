package com.example.domain.usecase.students

import com.example.domain.repository.StudentsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class DuplicateCheckIdUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studentsRepository.duplicateCheckId(data)
    }
}