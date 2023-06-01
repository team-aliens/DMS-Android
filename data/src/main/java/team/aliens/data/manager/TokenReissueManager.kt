package team.aliens.data.manager

import team.aliens.domain.model.auth.Token

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): Token
}
