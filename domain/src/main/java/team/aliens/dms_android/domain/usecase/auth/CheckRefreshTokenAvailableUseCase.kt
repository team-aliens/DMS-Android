package team.aliens.dms_android.domain.usecase.auth

import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class CheckRefreshTokenAvailableUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.checkRefreshTokenAvailable()
    }
}
