package team.aliens.domain.param

import team.aliens.domain.enums.EmailType

data class CheckEmailCodeParam(
    val email: String,
    val authCode: String,
    val type: EmailType,
)