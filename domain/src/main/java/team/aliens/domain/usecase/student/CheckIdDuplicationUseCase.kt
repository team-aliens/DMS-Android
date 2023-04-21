package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.CheckIdDuplicationInput
import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class CheckIdDuplicationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        checkIdDuplicationInput: CheckIdDuplicationInput,
    ) {
        studentRepository.checkIdDuplication(
            input = checkIdDuplicationInput,
        )
    }
}
