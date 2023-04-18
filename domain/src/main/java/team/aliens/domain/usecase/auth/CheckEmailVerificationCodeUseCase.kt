package team.aliens.domain.usecase.auth

import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class CheckEmailVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) {
        authRepository.checkEmailVerificationCode(
            email = email,
            authCode = authCode,
            type = type,
        )
    }
}
