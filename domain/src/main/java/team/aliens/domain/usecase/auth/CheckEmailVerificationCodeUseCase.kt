package team.aliens.domain.usecase.auth

import team.aliens.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class CheckEmailVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        checkEmailVerificationCodeInput: CheckEmailVerificationCodeInput,
    ) {
        authRepository.checkEmailVerificationCode(
            input = checkEmailVerificationCodeInput,
        )
    }
}
