package team.aliens.dms.android.core.jwt

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseAccessTokenException
import team.aliens.dms.android.core.jwt.exception.CannotUseRefreshTokenException
import team.aliens.dms.android.core.jwt.network.JwtReissueManager
import team.aliens.dms.android.core.jwt.network.exception.CannotReissueTokenException
import javax.inject.Inject

internal class JwtProviderImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
    private val jwtReissueManager: JwtReissueManager,
) : JwtProvider() {
    private val tokenMutex = Mutex()

    private var _cachedAccessToken: AccessToken? = null
    override val cachedAccessToken: AccessToken
        get() {
            if (this._cachedAccessToken == null) {
                throw CannotUseAccessTokenException()
            }
            if (this._cachedAccessToken!!.isExpired()) {
                runBlocking(Dispatchers.IO) { this@JwtProviderImpl.reissueTokens() }
            }
            val accessToken = _cachedAccessToken ?: throw CannotUseAccessTokenException()

            if (accessToken.isExpired()) {
                throw CannotUseAccessTokenException()
            }

            return accessToken
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
            CoroutineScope(Dispatchers.IO).launch { reissueTokens() }
        }
    }

    private fun loadTokens() {
        runCatching {
            jwtDataStoreDataSource.loadTokens()
        }.onSuccess { tokens ->
            this@JwtProviderImpl._cachedAccessToken = tokens.accessToken
            this@JwtProviderImpl._cachedRefreshToken = tokens.refreshToken
        }.onFailure { exception ->
            Log.e("JwtProvider", "Failed to persist tokens", exception)
        }
        this.refreshTokenAbility()
    }

    override fun updateTokens(tokens: Tokens) {
        runBlocking {
            tokenMutex.withLock {
                updateTokensLocked(tokens = tokens)
            }
        }
    }

    override fun clearCaches() {
        runBlocking {
            tokenMutex.withLock {
                clearCachesLocked()
            }
        }
    }

    private fun refreshTokenAbility() {
        _isCachedAccessTokenAvailable.value = checkIsAccessTokenAvailable()
        _isCachedRefreshTokenAvailable.value = checkIsRefreshTokenAvailable()
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

    private suspend fun reissueTokens() {
        tokenMutex.withLock {
            val accessToken = this@JwtProviderImpl._cachedAccessToken
                ?: return@withLock

            if (!accessToken.isExpired()) {
                return@withLock
            }

            try {
                val tokens = jwtReissueManager(refreshToken = cachedRefreshToken.value)
                updateTokensLocked(tokens = tokens)
            } catch (exception: CannotReissueTokenException) {
                if (exception.statusCode == 401 || exception.statusCode == 404) {
                    clearCachesLocked()
                }
            } catch (_: CannotUseRefreshTokenException) {
                clearCachesLocked()
            }
            this@JwtProviderImpl.refreshTokenAbility()
        }
    }

    private suspend fun updateTokensLocked(tokens: Tokens) {
        val previousAccessToken = _cachedAccessToken
        val previousRefreshToken = _cachedRefreshToken

        this._cachedAccessToken = tokens.accessToken
        this._cachedRefreshToken = tokens.refreshToken
        this.refreshTokenAbility()

        runCatching {
            jwtDataStoreDataSource.storeTokens(tokens = tokens)
        }.onFailure { exception ->
            this._cachedAccessToken = previousAccessToken
            this._cachedRefreshToken = previousRefreshToken
            this.refreshTokenAbility()

            Log.e("JwtProvider", "Failed to store tokens", exception)
            throw IllegalStateException("Failed to persist tokens", exception)
        }
    }

    private suspend fun clearCachesLocked() {
        val previousAccessToken = _cachedAccessToken
        val previousRefreshToken = _cachedRefreshToken

        this._cachedAccessToken = null
        this._cachedRefreshToken = null
        this.refreshTokenAbility()

        runCatching {
            jwtDataStoreDataSource.clearTokens()
        }.onFailure { exception ->
            this._cachedAccessToken = previousAccessToken
            this._cachedRefreshToken = previousRefreshToken
            this.refreshTokenAbility()

            Log.e("JwtProvider", "Failed to clear tokens", exception)
            throw IllegalStateException("Failed to clear persisted tokens", exception)
        }
    }
}
