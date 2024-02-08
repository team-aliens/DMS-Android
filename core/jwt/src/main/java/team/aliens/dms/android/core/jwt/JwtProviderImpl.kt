package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseAccessTokenException
import team.aliens.dms.android.core.jwt.exception.CannotUseRefreshTokenException
import team.aliens.dms.android.core.jwt.network.JwtReissueManager
import javax.inject.Inject

internal class JwtProviderImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
    private val jwtReissueManager: JwtReissueManager
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

    private val _isCachedAccessTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
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

    private val _isCachedRefreshTokenAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> =
        _isCachedRefreshTokenAvailable.asStateFlow()

    init {
        loadTokens()
    }

    private fun loadTokens() {
        runCatching {
            jwtDataStoreDataSource.loadTokens()
        }.onSuccess { tokens ->
            this@JwtProviderImpl._cachedAccessToken = tokens.accessToken
            this@JwtProviderImpl._cachedRefreshToken = tokens.refreshToken
        }.onFailure {
            // TODO: handle when token not found
        }
        this.updateTokensAbility()
    }

    override fun updateTokens(tokens: Tokens) {
        this._cachedAccessToken = tokens.accessToken
        this._cachedRefreshToken = tokens.refreshToken
        CoroutineScope(Dispatchers.Default).launch {
            jwtDataStoreDataSource.storeTokens(tokens = tokens)
        }
        this.updateTokensAbility()
    }

    override fun clearCaches() {
        this._cachedAccessToken = null
        this._cachedRefreshToken = null
        CoroutineScope(Dispatchers.Default).launch {
            jwtDataStoreDataSource.clearTokens()
        }
        this.updateTokensAbility()
    }

    private fun updateTokensAbility() {
        CoroutineScope(Dispatchers.Default).launch {
            _isCachedAccessTokenAvailable.emit(this@JwtProviderImpl.checkIsAccessTokenAvailable())
            _isCachedRefreshTokenAvailable.emit(this@JwtProviderImpl.checkIsRefreshTokenAvailable())
        }
    }

    private fun checkIsAccessTokenAvailable(): Boolean {
        if (_cachedAccessToken == null) {
            return false
        }
        return _cachedAccessToken!!.isExpired()
    }

    private fun checkIsRefreshTokenAvailable(): Boolean {
        if (_cachedRefreshToken == null) {
            return false
        }
        return _cachedRefreshToken!!.isExpired()
    }

    private fun reissueTokens() = runBlocking {
        runCatching {
            this@JwtProviderImpl.clearCaches()
            jwtReissueManager(refreshToken = cachedRefreshToken.value)
        }.onSuccess { tokens ->
            this@JwtProviderImpl.updateTokens(tokens = tokens)
        }
        this@JwtProviderImpl.updateTokensAbility()
    }
}

/*

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

    override fun clearCaches() {
        runBlocking {
            jwtDataStoreDataSource.clearTokens()
            withContext(Dispatchers.Default) {
                updateCachedAccessTokenAvailable(false)


                updateCachedRefreshTokenAvailable(false)
            }
        }
    }
}
*/
