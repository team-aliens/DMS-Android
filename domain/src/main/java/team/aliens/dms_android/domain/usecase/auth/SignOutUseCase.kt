package team.aliens.dms_android.domain.usecase.auth

import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.clearToken()
    }
}
