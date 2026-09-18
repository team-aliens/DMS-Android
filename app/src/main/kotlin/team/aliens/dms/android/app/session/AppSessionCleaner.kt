package team.aliens.dms.android.app.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.database.DmsDatabase
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.SessionCleaner
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.core.widget.MealWorker
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
) : SessionCleaner() {

    private val isCleaning = AtomicBoolean(false)

    override suspend fun clearSession(tokensAlreadyCleared: Boolean) {
        if (!isCleaning.compareAndSet(false, true)) {
            return
        }

        try {
            withContext(Dispatchers.IO + NonCancellable) {
                if (!tokensAlreadyCleared) {
                    jwtProvider.get().clearCaches()
                }
                schoolProvider.get().clearCaches()
                database.clearAllTables()
                MealWorker.clear(context)
            }
        } finally {
            isCleaning.set(false)
        }
    }
}
