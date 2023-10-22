package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.network.TokenReissueManager
import team.aliens.dms.android.shared.date.util.now
import javax.inject.Inject

// TODO: JWT Repository 구현 고민
internal class JwtProviderImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
    private val tokenReissueManager: TokenReissueManager,
) : JwtProvider() {

    private var _cachedAccessToken: AccessToken? = null
    override val cachedAccessToken: AccessToken
        get() = if (checkCachedAccessTokenAvailable().also(::updateCachedAccessTokenAvailable)) {
            _cachedAccessToken!!
        } else {
            this.fetchTokens().accessToken
        }

    private var _cachedAccessTokenExpiration: AccessTokenExpiration? = null
    override val cachedAccessTokenExpiration: AccessTokenExpiration
        get() = _cachedAccessTokenExpiration ?: this.fetchTokens().accessTokenExpiration

    private val _isCachedAccessTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isCachedAccessTokenAvailable: StateFlow<Boolean> =
        _isCachedAccessTokenAvailable.asStateFlow()

    private var _cachedRefreshToken: RefreshToken? = null
    override val cachedRefreshToken: RefreshToken
        get() = if (checkCachedRefreshTokenAvailable().also(::updateCachedRefreshTokenAvailable)) {
            _cachedRefreshToken!!
        } else {
            this.fetchTokens().refreshToken
        }

    private var _cachedRefreshTokenExpiration: RefreshTokenExpiration? = null
    override val cachedRefreshTokenExpiration: RefreshTokenExpiration
        get() = _cachedRefreshTokenExpiration ?: this.fetchTokens().refreshTokenExpiration

    private val _isCachedRefreshTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> =
        _isCachedRefreshTokenAvailable.asStateFlow()

    init {
        loadTokens()
    }

    private fun checkCachedAccessTokenAvailable(): Boolean {
        val con1 = _cachedAccessToken != null
        val con2 = _cachedRefreshTokenExpiration != null
        val con3 = now.isBefore(_cachedAccessTokenExpiration)

        return con1 && con2 && con3
    }

    private fun checkCachedRefreshTokenAvailable(): Boolean {
        val con1 = _cachedRefreshToken != null
        val con2 = _cachedRefreshTokenExpiration != null
        val con3 = now.isBefore(_cachedRefreshTokenExpiration)

        return con1 && con2 && con3
    }

    private fun updateCachedAccessTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedAccessTokenAvailable.emit(available) }
    }

    private fun updateCachedRefreshTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedRefreshTokenAvailable.emit(available) }
    }

    override fun saveTokens(tokens: Tokens) {
        CoroutineScope(Dispatchers.Default).launch { jwtDataStoreDataSource.storeTokens(tokens) }
    }

    private fun loadTokens(): Tokens = jwtDataStoreDataSource.loadTokens().also(::updateTokens)

    private fun fetchTokens(): Tokens =
        tokenReissueManager(refreshToken = jwtDataStoreDataSource.loadRefreshToken())
            .toModel()
            .also(::updateTokens)

    private fun updateTokens(tokens: Tokens) {
        this._cachedAccessToken = tokens.accessToken
        this._cachedAccessTokenExpiration = tokens.accessTokenExpiration
        this._cachedRefreshToken = tokens.refreshToken
        this._cachedRefreshTokenExpiration = tokens.refreshTokenExpiration

        CoroutineScope(Dispatchers.IO).launch {
            jwtDataStoreDataSource.storeTokens(tokens)
            withContext(Dispatchers.Default) {
                updateCachedAccessTokenAvailable(true)
                updateCachedRefreshTokenAvailable(true)
            }
        }
    }
}
