package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseAccessTokenException
import team.aliens.dms.android.core.jwt.exception.CannotUseRefreshTokenException
import team.aliens.dms.android.core.jwt.network.JwtReissueManager
import javax.inject.Inject

internal class JwtProviderImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
    private val jwtReissueManager: JwtReissueManager,
) : JwtProvider() {
    private var _cachedAccessToken: AccessToken? = null
    override val cachedAccessToken: AccessToken
        get() {
            if (this._cachedAccessToken == null) {
                throw CannotUseAccessTokenException()
            }
            if (this._cachedAccessToken!!.isExpired()) {
                this.reissueTokens()
            }
            return _cachedAccessToken!!
        }

    private val _isCachedAccessTokenAvailable: MutableStateFlow<Boolean> =
        MutableStateFlow(checkIsAccessTokenAvailable())
    override val isCachedAccessTokenAvailable: StateFlow<Boolean> =
        _isCachedAccessTokenAvailable.asStateFlow()

    private var _cachedRefreshToken: RefreshToken? = null
    override val cachedRefreshToken: RefreshToken
        get() {
            if (_cachedRefreshToken == null) {
                throw CannotUseRefreshTokenException()
            }
            if (_cachedRefreshToken!!.isExpired()) {
                throw CannotUseRefreshTokenException()
            }
            return _cachedRefreshToken!!
        }

    private val _isCachedRefreshTokenAvailable: MutableStateFlow<Boolean> =
        MutableStateFlow(checkIsRefreshTokenAvailable())
    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> =
        _isCachedRefreshTokenAvailable.asStateFlow()

    init {
        runCatching {
            this.loadTokens()
        }.onSuccess {
            this.reissueTokens()
        }
    }

    private fun loadTokens() {
        runCatching {
            jwtDataStoreDataSource.loadTokens()
        }.onSuccess { tokens ->
            this@JwtProviderImpl._cachedAccessToken = tokens.accessToken
            this@JwtProviderImpl._cachedRefreshToken = tokens.refreshToken
        }
        this.refreshTokenAbility()
    }

    override fun updateTokens(tokens: Tokens) {
        this._cachedAccessToken = tokens.accessToken
        this._cachedRefreshToken = tokens.refreshToken
        this.refreshTokenAbility()
        CoroutineScope(Dispatchers.Default).launch {
            jwtDataStoreDataSource.storeTokens(tokens = tokens)
        }
    }

    override fun clearCaches() {
        this._cachedAccessToken = null
        this._cachedRefreshToken = null
        CoroutineScope(Dispatchers.Default).launch {
            jwtDataStoreDataSource.clearTokens()
        }
        this.refreshTokenAbility()
    }

    private fun refreshTokenAbility() {
        CoroutineScope(Dispatchers.Default).launch {
            _isCachedAccessTokenAvailable.emit(this@JwtProviderImpl.checkIsAccessTokenAvailable())
            _isCachedRefreshTokenAvailable.emit(this@JwtProviderImpl.checkIsRefreshTokenAvailable())
        }
    }

    private fun checkIsAccessTokenAvailable(): Boolean {
        if (this._cachedAccessToken == null) {
            return false
        }
        return !_cachedAccessToken!!.isExpired()
    }

    private fun checkIsRefreshTokenAvailable(): Boolean {
        if (this._cachedRefreshToken == null) {
            return false
        }
        return !_cachedRefreshToken!!.isExpired()
    }

    private fun reissueTokens() {
        runCatching {
            jwtReissueManager(refreshToken = cachedRefreshToken.value)
        }.onSuccess { tokens ->
            this@JwtProviderImpl.updateTokens(tokens = tokens)
        }
        this@JwtProviderImpl.refreshTokenAbility()
    }
}
