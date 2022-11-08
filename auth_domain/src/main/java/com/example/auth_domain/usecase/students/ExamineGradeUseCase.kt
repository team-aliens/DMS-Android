package com.example.auth_domain.usecase.students

import com.example.auth_domain.entity.ExamineGradeEntity
import com.example.auth_domain.param.ExamineGradeParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class ExamineGradeUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
): UseCase<ExamineGradeParam, ExamineGradeEntity>(){
    override suspend fun execute(data: ExamineGradeParam): ExamineGradeEntity =
        studentsRepository.examineGrade(data)
}