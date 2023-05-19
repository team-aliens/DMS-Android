package team.aliens.domain.usecase.auth

import team.aliens.domain.model._common.AuthenticationOutput
import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): AuthenticationOutput {
        return authRepository.autoSignIn()
    }
}
