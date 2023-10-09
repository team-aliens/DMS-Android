package team.aliens.dms_android.domain.usecase.student

import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.student.SignUpInput
import team.aliens.domain.repository.StudentRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        signUpInput: SignUpInput,
    ): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
        return studentRepository.signUp(
            input = signUpInput,
        )
    }
}
