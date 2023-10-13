package team.aliens.dms.android.core.jwt

import team.aliens.dms.android.core.jwt.store.JwtStore
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
