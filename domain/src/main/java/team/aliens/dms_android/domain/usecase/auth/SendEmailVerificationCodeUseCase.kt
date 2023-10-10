package team.aliens.dms_android.domain.usecase.auth

import team.aliens.dms_android.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.dms_android.domain.repository.AuthRepository
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
