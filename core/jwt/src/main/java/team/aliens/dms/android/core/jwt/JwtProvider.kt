package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.datastore.store.JwtStore
import team.aliens.dms.android.core.jwt.network.TokenReissueManager
import team.aliens.dms.android.shared.date.util.now
import javax.inject.Inject

object JwtProvider : JwtProviderInjectionDelegation() {

    private var _cachedAccessToken: AccessToken? = null
    val cachedAccessToken: AccessToken
        get() = if (_cachedAccessToken == null) {
            this.updateAccessToken()
        } else {
            this.fetchAccessToken()
        }

    // 재발급 및 저장 함수
    // 토큰 유효 확인 함수 (access, refresh 추상화)

    private var cachedAccessTokenExpiration: AccessTokenExpiration? = null

    private var cachedRefreshToken: RefreshToken? = null

    private var cachedRefreshTokenExpiration: RefreshTokenExpiration? = null

    private fun updateAccessToken(): AccessToken {
        val refreshToken = jwtStore.loadRefreshToken()
        val fetchedTokens = tokenReissueManager(refreshToken = refreshToken).toModel()

        CoroutineScope(Dispatchers.IO).launch {
            jwtStore.storeTokens(fetchedTokens)
        }

        return fetchedTokens.accessToken
    }

    private fun fetchAccessToken(): AccessToken {
        val accessTokenExpiration = jwtStore.loadAccessTokenExpiration()

        return if (accessTokenExpiration > now) {
            this.updateAccessToken()
        } else {
            jwtStore.loadAccessToken()
        }
    }
}

abstract class JwtProviderInjectionDelegation {

    @Inject
    lateinit var jwtStore: JwtStore

    @Inject
    lateinit var tokenReissueManager: TokenReissueManager
}
