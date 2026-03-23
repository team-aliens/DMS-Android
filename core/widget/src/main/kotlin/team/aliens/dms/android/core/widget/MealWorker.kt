package team.aliens.dms.android.core.widget

import android.content.Context
import android.os.Build
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import team.aliens.dms.android.data.meal.repository.MealRepository
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.today
import java.time.Duration
import java.util.concurrent.TimeUnit

@HiltWorker
class MealWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val mealRepository: MealRepository,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val uniqueWorkName = MealWorker::class.java.simpleName

        internal fun enqueue(context: Context) {
            val manager = WorkManager.getInstance(context)
            val request: PeriodicWorkRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PeriodicWorkRequestBuilder<MealWorker>(Duration.ofHours(1))
                    .setConstraints(createConstraints())
                    .build()
            } else {
                PeriodicWorkRequestBuilder<MealWorker>(1, TimeUnit.HOURS)
                    .setConstraints(createConstraints())
                    .build()
            }

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }

        private fun createConstraints(): Constraints =
            Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

        internal fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(MealGlanceWidget::class.java)

        return runCatching {
            setWidgetState(glanceIds, MealInfo.Loading)

            val mealDate = if (now.hour >= 19) today.plusDays(1) else today
            val response = mealRepository.fetchMeal(mealDate).getOrThrow()
            setWidgetState(glanceIds, response.toEntity())
            Result.success()
        }.getOrElse { throwable ->
            setWidgetState(glanceIds, MealInfo.Unavailable)
            android.util.Log.e("MealWorker", "Failed to update widget", throwable)
            Result.failure()
        }
    }

    private suspend fun setWidgetState(
        glanceIds: List<GlanceId>,
        newState: MealInfo,
    ) {
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                glanceId = glanceId,
                definition = MealInfoStateDefinition,
                updateState = { newState },
            )
        }
        MealGlanceWidget().updateAll(context)
    }
}
