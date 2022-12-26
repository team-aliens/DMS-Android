package com.example.domain.usecase.schools

import com.example.domain.param.SchoolAnswerParam
import com.example.domain.repository.SchoolsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolAnswerUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : UseCase<SchoolAnswerParam, Unit>() {
    override suspend fun execute(data: SchoolAnswerParam) {
        schoolsRepository.compareSchoolAnswer(data)
    }
}