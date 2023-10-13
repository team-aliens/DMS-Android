package team.aliens.dms.android.data.manager

import team.aliens.dms.android.domain.model._common.AuthenticationOutput

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): AuthenticationOutput
}
