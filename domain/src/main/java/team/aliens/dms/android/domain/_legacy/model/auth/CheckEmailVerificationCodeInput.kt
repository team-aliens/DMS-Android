package team.aliens.dms.android.domain._legacy.model.auth

import team.aliens.dms.android.domain.model._common.EmailVerificationType

data class CheckEmailVerificationCodeInput(
    val email: String,
    val authCode: String,
    val type: EmailVerificationType,
)
