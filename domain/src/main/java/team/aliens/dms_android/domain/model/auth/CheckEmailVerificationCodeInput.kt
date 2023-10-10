package team.aliens.dms_android.domain.model.auth

import team.aliens.dms_android.domain.model._common.EmailVerificationType

data class CheckEmailVerificationCodeInput(
    val email: String,
    val authCode: String,
    val type: EmailVerificationType,
)
