package team.aliens.dms.android.core.jwt

abstract class SessionCleaner {
    abstract suspend fun unregisterDeviceToken()

    abstract suspend fun clearSession(
        tokensAlreadyCleared: Boolean = false,
        shouldUnregisterDeviceToken: Boolean = true,
    )
}
