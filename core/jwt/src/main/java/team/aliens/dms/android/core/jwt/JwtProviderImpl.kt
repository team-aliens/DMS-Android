package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.datastore.exception.LoadFailureException
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseTokensException
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
        get() = if (checkCachedAccessTokenAvailable().also(::updateCachedRefreshTokenAvailable)) {
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
        val con2 = now.isBefore(cachedAccessTokenExpiration)

        return con1 && con2
    }

    private fun updateCachedAccessTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedAccessTokenAvailable.emit(available) }
    }

    private fun updateCachedRefreshTokenAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isCachedRefreshTokenAvailable.emit(available) }
    }

    private fun loadTokens(): Tokens = try {
        jwtDataStoreDataSource.loadTokens().also(::updateTokens)
    } catch (e: LoadFailureException) {
        throw CannotUseTokensException()
    }

    private fun fetchTokens(): Tokens = try {
        val refreshToken = jwtDataStoreDataSource.loadRefreshToken()
        tokenReissueManager(refreshToken).toModel().also(::updateTokens)
    } catch (e: LoadFailureException) {
        throw CannotUseTokensException()
    }

    private fun updateTokens(tokens: Tokens) {
        this._cachedAccessToken = tokens.accessToken
        this._cachedAccessTokenExpiration = tokens.accessTokenExpiration
        CoroutineScope(Dispatchers.IO).launch {
            updateCachedAccessTokenAvailable(true)
            updateCachedRefreshTokenAvailable(true)
            jwtDataStoreDataSource.storeTokens(tokens)
        }
    }
}