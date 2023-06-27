package team.aliens.data.manager

import team.aliens.domain.model._common.AuthenticationOutput

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): AuthenticationOutput
}
