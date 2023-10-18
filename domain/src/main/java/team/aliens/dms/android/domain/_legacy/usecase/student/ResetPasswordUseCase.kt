package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain.model.student.ResetPasswordInput
import team.aliens.dms.android.domain.repository.StudentRepository
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
