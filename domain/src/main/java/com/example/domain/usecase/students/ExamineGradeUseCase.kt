package com.example.domain.usecase.students

import com.example.domain.entity.user.ExamineGradeEntity
import com.example.domain.param.ExamineGradeParam
import com.example.domain.repository.StudentsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class ExamineGradeUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
): UseCase<ExamineGradeParam, ExamineGradeEntity>(){
    override suspend fun execute(data: ExamineGradeParam): ExamineGradeEntity =
        studentsRepository.examineGrade(data)
}