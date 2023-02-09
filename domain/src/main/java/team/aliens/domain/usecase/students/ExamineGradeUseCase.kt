package team.aliens.domain.usecase.students

import kotlinx.coroutines.flow.Flow
import team.aliens.domain.entity.user.ExamineGradeEntity
import team.aliens.domain.param.ExamineGradeParam
import team.aliens.domain.repository.StudentsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class ExamineGradeUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) : UseCase<ExamineGradeParam, Flow<ExamineGradeEntity>>() {
    override suspend fun execute(data: ExamineGradeParam): Flow<ExamineGradeEntity> =
        studentsRepository.examineGrade(data)
}