package team.aliens.domain.usecase.students

import com.example.domain.entity.user.ExamineGradeEntity
import com.example.domain.param.ExamineGradeParam
import com.example.domain.repository.StudentsRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExamineGradeUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
): UseCase<ExamineGradeParam, Flow<ExamineGradeEntity>>(){
    override suspend fun execute(data: ExamineGradeParam): Flow<ExamineGradeEntity> =
        studentsRepository.examineGrade(data)
}