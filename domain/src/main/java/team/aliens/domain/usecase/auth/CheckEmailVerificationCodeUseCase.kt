package team.aliens.domain.usecase.auth

import team.aliens.domain._model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain._repository.AuthRepository
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
