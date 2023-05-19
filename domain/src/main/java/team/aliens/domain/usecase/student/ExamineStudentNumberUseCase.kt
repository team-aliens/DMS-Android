package team.aliens.domain.usecase.student

import team.aliens.domain.model.student.ExamineStudentNumberInput
import team.aliens.domain.model.student.ExamineStudentNumberOutput
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class ExamineStudentNumberUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        examineStudentNumberInput: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput {
        return studentRepository.examineStudentNumber(
            input = examineStudentNumberInput,
        )
    }
}
