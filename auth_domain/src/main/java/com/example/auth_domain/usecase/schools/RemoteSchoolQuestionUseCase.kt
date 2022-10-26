package com.example.auth_domain.usecase.schools

import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.auth_domain.repository.SchoolsRepository
import com.example.auth_domain.usecase.UseCase
import java.util.UUID
import javax.inject.Inject

class RemoteSchoolQuestionUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : UseCase<UUID, SchoolConfirmQuestionEntity>() {
    override suspend fun execute(data: UUID): SchoolConfirmQuestionEntity =
        schoolsRepository.schoolQuestion(data)
}