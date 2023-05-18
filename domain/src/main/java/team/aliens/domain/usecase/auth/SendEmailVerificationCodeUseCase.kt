package team.aliens.domain.usecase.auth

import team.aliens.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.domain.repository.AuthRepository
import javax.inject.Inject

class SendEmailVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        sendEmailVerificationCodeInput: SendEmailVerificationCodeInput,
    ) {
        authRepository.sendEmailVerificationCode(
            input = sendEmailVerificationCodeInput,
        )
    }
}
