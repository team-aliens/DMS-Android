package team.aliens.domain.usecase.student

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.SignUpInput
import team.aliens.domain._repository.StudentRepository
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
