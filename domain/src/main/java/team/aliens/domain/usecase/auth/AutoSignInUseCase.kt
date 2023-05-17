package team.aliens.domain.usecase.auth

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): AuthenticationOutput {
        return authRepository.autoSignIn()
    }
}
