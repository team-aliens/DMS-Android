package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.student.ExamineStudentNumberInput
import team.aliens.dms.android.domain.model.student.ExamineStudentNumberOutput
import team.aliens.dms.android.domain.repository.StudentRepository
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
