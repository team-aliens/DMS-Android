package com.example.auth_domain.usecase.schools

import com.example.auth_domain.param.SchoolAnswerParam
import com.example.auth_domain.repository.SchoolsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolAnswerUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : UseCase<SchoolAnswerParam, Unit>() {
    override suspend fun execute(data: SchoolAnswerParam) {
        schoolsRepository.compareSchoolAnswer(data)
    }
}