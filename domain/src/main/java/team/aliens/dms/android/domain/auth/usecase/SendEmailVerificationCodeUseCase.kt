package team.aliens.dms.android.domain.auth.usecase

import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.repository.AuthRepository
import javax.inject.Inject

class SendEmailVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        type: EmailVerificationType,
    ) {
        authRepository.sendEmailVerificationCode(email, type)
    }
}
