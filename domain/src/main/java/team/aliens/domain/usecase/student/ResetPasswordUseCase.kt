package team.aliens.domain.usecase.student

import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        resetPasswordInput: ResetPasswordInput,
    ) {
        studentRepository.resetPassword(
            input = resetPasswordInput,
        )
    }
}
