package team.aliens.data.manager

import team.aliens.dms_android.domain.model._common.AuthenticationOutput

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): team.aliens.dms_android.domain.model._common.AuthenticationOutput
}
