package team.aliens.domain.param

import team.aliens.domain.enums.EmailType

data class RequestEmailCodeParam(
    val email: String,
    val type: EmailType,
)
