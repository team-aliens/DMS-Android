package team.aliens.domain.usecase.auth

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        signInInput: SignInInput,
    ): AuthenticationOutput {
        return authRepository.signIn(
            input = signInInput,
        )
    }
}
