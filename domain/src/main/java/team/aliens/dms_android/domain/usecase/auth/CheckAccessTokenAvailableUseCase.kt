package team.aliens.dms_android.domain.usecase.auth

import team.aliens.dms_android.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAccessTokenAvailableUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.checkAccessTokenAvailable()
    }
}