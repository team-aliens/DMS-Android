package team.aliens.domain.usecase.auth

import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class ReissueTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.reissueToken()
    }
}
