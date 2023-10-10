package team.aliens.dms_android.data.manager

interface TokenReissueManager {
    fun reissueToken(refreshToken: String): team.aliens.dms_android.domain.model._common.AuthenticationOutput
}
