package team.aliens.domain.usecase.student

import team.aliens.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.student.SignUpInput
import team.aliens.domain.repository.StudentRepository
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
