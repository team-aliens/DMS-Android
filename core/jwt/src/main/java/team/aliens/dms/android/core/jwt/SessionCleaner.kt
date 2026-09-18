package team.aliens.dms.android.core.jwt

abstract class SessionCleaner {
    abstract suspend fun clearSession(tokensAlreadyCleared: Boolean = false)
}
