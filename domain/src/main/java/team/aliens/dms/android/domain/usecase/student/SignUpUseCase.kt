package team.aliens.dms.android.domain.usecase.student

import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.student.SignUpInput
import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        signUpInput: SignUpInput,
    ): AuthenticationOutput {
        return studentRepository.signUp(
            input = signUpInput,
        )
    }
}
