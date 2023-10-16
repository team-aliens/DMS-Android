package team.aliens.dms.android.domain.usecase.auth

import team.aliens.dms.android.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.dms.android.domain.repository.AuthRepository
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