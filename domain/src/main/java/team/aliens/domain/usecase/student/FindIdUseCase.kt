package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._repository.StudentRepository
import java.util.UUID
import javax.inject.Inject

class FindIdUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput {
        return studentRepository.findId(
            schoolId = schoolId,
            studentName = studentName,
            grade = grade,
            classRoom = classRoom,
            number = number,
        )
    }
}
