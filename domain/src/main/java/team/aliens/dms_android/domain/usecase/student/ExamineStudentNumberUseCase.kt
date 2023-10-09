package team.aliens.dms_android.domain.usecase.student

import team.aliens.dms_android.domain.model.student.ExamineStudentNumberInput
import team.aliens.dms_android.domain.model.student.ExamineStudentNumberOutput
import team.aliens.dms_android.domain.repository.StudentRepository
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
