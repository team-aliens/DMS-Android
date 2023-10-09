package team.aliens.dms_android.domain.usecase.auth

import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.auth.SignInInput
import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        signInInput: SignInInput,
    ): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
        return authRepository.signIn(
            input = signInInput,
        )
    }
}
