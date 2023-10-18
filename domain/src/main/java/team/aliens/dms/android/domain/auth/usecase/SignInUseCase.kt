package team.aliens.dms.android.domain.auth.usecase

import team.aliens.dms.android.data.auth.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        accountId: String,
        password: String,
    ) {
        authRepository.signIn(accountId, password)
    }
}
