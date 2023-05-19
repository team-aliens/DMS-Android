package team.aliens.domain.model.auth

import team.aliens.domain.model._common.EmailVerificationType

data class CheckEmailVerificationCodeInput(
    val email: String,
    val authCode: String,
    val type: EmailVerificationType,
)
