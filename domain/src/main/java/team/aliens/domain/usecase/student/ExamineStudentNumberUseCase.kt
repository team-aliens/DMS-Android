package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.ExamineStudentNumberOutput
import team.aliens.domain._repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

class ExamineStudentNumberUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberOutput {
        return studentRepository.examineStudentNumber(
            schoolId = schoolId,
            grade = grade,
            classRoom = classRoom,
            number = number,
        )
    }
}
