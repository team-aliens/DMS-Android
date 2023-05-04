package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.CheckEmailDuplicationInput
import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class CheckEmailDuplicationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        checkEmailDuplicationInput: CheckEmailDuplicationInput,
    ) {
        studentRepository.checkEmailDuplication(
            input = checkEmailDuplicationInput,
        )
    }
}
