package team.aliens.domain.usecase.auth

import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.autoSignIn()
    }
}
