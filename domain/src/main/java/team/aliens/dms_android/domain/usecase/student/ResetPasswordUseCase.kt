package team.aliens.dms_android.domain.usecase.student

import team.aliens.domain.model.student.ResetPasswordInput
import team.aliens.domain.repository.StudentRepository
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
