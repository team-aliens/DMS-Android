package team.aliens.domain._model.auth

import team.aliens.domain._model._common.EmailVerificationType

data class CheckEmailVerificationCodeInput(
    val email: String,
    val authCode: String,
    val type: EmailVerificationType,
)
