package team.aliens.dms.android.app.session

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.database.DmsDatabase
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.SessionCleaner
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.core.widget.MealWorker
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
internal class AppSessionCleaner @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: DmsDatabase,
    private val jwtProvider: Provider<JwtProvider>,
    private val schoolProvider: Provider<SchoolProvider>,
    private val notificationRepository: Provider<NotificationRepository>,
) : SessionCleaner() {

    private val isCleaning = AtomicBoolean(false)

    override suspend fun unregisterDeviceToken() {
        runCatchingCancellable {
            val repository = notificationRepository.get()
            val deviceToken = repository.getDeviceToken().getOrThrow()
            repository.cancelDeviceTokenRegistration(deviceToken).getOrThrow()
        }.onFailure { exception ->
            Log.e(TAG, "Failed to cancel device token registration", exception)
        }
    }

    override suspend fun clearSession(
        tokensAlreadyCleared: Boolean,
        shouldUnregisterDeviceToken: Boolean,
    ) {
        if (!isCleaning.compareAndSet(false, true)) {
            return
        }

        try {
            withContext(Dispatchers.IO + NonCancellable) {
                val failures = mutableListOf<Throwable>()

                if (shouldUnregisterDeviceToken && !tokensAlreadyCleared) {
                    unregisterDeviceToken()
                }
                if (!tokensAlreadyCleared) {
                    runCatchingCancellable {
                        jwtProvider.get().clearCaches()
                    }.exceptionOrNull()?.let(failures::add)
                }
                runCatchingCancellable {
                    schoolProvider.get().clearCaches()
                }.exceptionOrNull()?.let(failures::add)
                runCatchingCancellable {
                    database.clearAllTables()
                }.exceptionOrNull()?.let(failures::add)
                runCatchingCancellable {
                    MealWorker.clear(context)
                }.exceptionOrNull()?.let(failures::add)

                failures.firstOrNull()?.let { firstFailure ->
                    failures.drop(1).forEach(firstFailure::addSuppressed)
                    throw firstFailure
                }
            }
        } finally {
            isCleaning.set(false)
        }
    }

    private companion object {
        const val TAG = "AppSessionCleaner"
    }
}
