package team.aliens.data.util

import team.aliens.domain.model.auth.Token

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): Token
}
