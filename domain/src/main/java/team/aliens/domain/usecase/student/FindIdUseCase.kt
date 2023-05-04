package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.FindIdInput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._repository.StudentRepository
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
