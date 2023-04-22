package team.aliens.domain._model.auth

import team.aliens.domain._model._common.EmailVerificationType

/**
 * A request when sending email verification code
 * @property email an email which the verification code will be sent
 * @property type a type of verification email
 */
data class SendEmailVerificationCodeInput(
    val email: String,
    val type: EmailVerificationType,
)
