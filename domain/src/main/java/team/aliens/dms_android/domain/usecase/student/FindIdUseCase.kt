package team.aliens.dms_android.domain.usecase.student

import team.aliens.domain.model.student.FindIdInput
import team.aliens.domain.model.student.FindIdOutput
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class FindIdUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        findIdInput: FindIdInput,
    ): FindIdOutput {
        return studentRepository.findId(
            input = findIdInput,
        )
    }
}
