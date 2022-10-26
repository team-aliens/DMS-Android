package com.example.auth_domain.usecase.schools

import com.example.auth_domain.entity.SchoolIdEntity
import com.example.auth_domain.repository.SchoolsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolCodeUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : UseCase<String, SchoolIdEntity>() {
    override suspend fun execute(data: String): SchoolIdEntity =
        schoolsRepository.examineSchoolCode(data)
}