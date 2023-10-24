package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseTokensException
import team.aliens.dms.android.core.jwt.network.TokenReissueManager
import team.aliens.dms.android.shared.date.util.now
import javax.inject.Inject

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

    override lateinit var cachedAccessTokenExpiration: AccessTokenExpiration
        private set

    private val _isCachedAccessTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isCachedAccessTokenAvailable: StateFlow<Boolean> =
        _isCachedAccessTokenAvailable.asStateFlow()

    private var _cachedRefreshToken: RefreshToken? = null
    override val cachedRefreshToken: RefreshToken
        get() = if (checkCachedRefreshTokenAvailable().also(::updateCachedRefreshTokenAvailable)) {
            _cachedRefreshToken!!
        } else {
            throw CannotUseTokensException()
        }

    override lateinit var cachedRefreshTokenExpiration: RefreshTokenExpiration
        private set

    private val _isCachedRefreshTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> =
        _isCachedRefreshTokenAvailable.asStateFlow()

    init {
        try {
            loadTokens()
        } catch (_: Exception) {
        }
    }

    private fun checkCachedAccessTokenAvailable(): Boolean {
        val con1 = _cachedAccessToken != null
        val con2 = now.isBefore(cachedAccessTokenExpiration)
        return con1 && con2
    }

    private fun checkCachedRefreshTokenAvailable(): Boolean {
        val con1 = _cachedRefreshToken != null
        val con2 = now.isBefore(cachedRefreshTokenExpiration)
        return con1 && con2
    }

    private fun updateCachedAccessTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedAccessTokenAvailable.emit(available) }
    }

    private fun updateCachedRefreshTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedRefreshTokenAvailable.emit(available) }
    }

    private fun loadTokens(): Tokens = jwtDataStoreDataSource.loadTokens().also(::updateTokens)

    private fun fetchTokens(): Tokens =
        tokenReissueManager(refreshToken = cachedRefreshToken).toModel()
            .also(::updateTokens)

    override fun updateTokens(tokens: Tokens) {
        this._cachedAccessToken = tokens.accessToken
        this.cachedAccessTokenExpiration = tokens.accessTokenExpiration
        this._cachedRefreshToken = tokens.refreshToken
        this.cachedRefreshTokenExpiration = tokens.refreshTokenExpiration

        runBlocking {
            jwtDataStoreDataSource.storeTokens(tokens)
            withContext(Dispatchers.Default) {
                updateCachedAccessTokenAvailable(true)
                updateCachedRefreshTokenAvailable(true)
            }
        }
    }
}
