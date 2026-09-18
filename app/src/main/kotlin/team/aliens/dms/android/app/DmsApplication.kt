package team.aliens.dms.android.app

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.widget.MealWorker
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

@HiltAndroidApp
class DmsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory: HiltWorkerFactory

    @Inject
    lateinit var jwtProvider: JwtProvider

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            jwtProvider.isCachedRefreshTokenAvailable.collect { isAvailable ->
                runCatchingCancellable {
                    if (isAvailable) {
                        MealWorker.enqueue(this@DmsApplication)
                    } else {
                        MealWorker.clear(this@DmsApplication)
                    }
                }.onFailure { exception ->
                    Log.e(TAG, "Failed to update meal widget work", exception)
                }
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()

    private companion object {
        const val TAG = "DmsApplication"
    }
}
