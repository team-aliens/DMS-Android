package team.aliens.dms_android.core.jwt

import team.aliens.dms_android.core.jwt.store.JwtStore
import javax.inject.Inject

object JwtProvider : JwtProviderInjectionDelegation() {
    /*
        val cachedAccessToken: AccessToken
        val cachedRefreshToken: RefreshToken
    */
}

abstract class JwtProviderInjectionDelegation {
    @Inject
    lateinit var jwtStore: JwtStore
}
