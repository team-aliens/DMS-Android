package team.aliens.dms.android.domain.usecase.auth

import team.aliens.dms.android.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAutoSignInAvailableUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean {
        val autoSignInEnabled = authRepository.findAutoSignInOption()
        val refreshTokenAvailable = authRepository.checkRefreshTokenAvailable()
        return autoSignInEnabled && refreshTokenAvailable
    }
}