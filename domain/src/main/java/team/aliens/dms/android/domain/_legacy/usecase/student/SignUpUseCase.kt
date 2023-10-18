package team.aliens.dms.android.domain._legacy.usecase.student

import team.aliens.dms.android.domain._legacy.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.student.SignUpInput
import team.aliens.dms.android.domain.repository.StudentRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        signUpInput: SignUpInput,
    ): _root_ide_package_.team.aliens.dms.android.domain._legacy.model._common.AuthenticationOutput {
        return studentRepository.signUp(
            input = signUpInput,
        )
    }
}
