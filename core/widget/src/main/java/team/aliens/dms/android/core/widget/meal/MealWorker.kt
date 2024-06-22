package team.aliens.dms.android.core.widget.meal

import android.content.Context
import android.os.Build
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.hiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import team.aliens.dms.android.core.widget.meal.mapper.toEntity
import team.aliens.dms.android.data.meal.repository.MealRepository
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.today
import java.time.Duration

@dagger.hiltWorker
class MealWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val mealRepository: MealRepository,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val uniqueWorkName = MealWorker::class.java.simpleName

        internal fun enqueue(context: Context) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PeriodicWorkRequestBuilder<MealWorker>(Duration.ofHours(8))
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val constrains = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.KEEP,
                requestBuilder
                    .setConstraints(constrains)
                    .build(),
            )
        }

        internal fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(MealGlanceWidget::class.java)

        return try {
            setWidgetState(glanceIds, MealInfo.Loading)

            try {
                val mealDate = if (now.hour > 19) today.plusDays(1) else today
                val response = mealRepository.fetchMeal(mealDate)
                setWidgetState(glanceIds, response.toEntity())
            } catch (e: Exception) {
                Result.failure()
            }
            Result.success()
        } catch (e: Exception) {
            setWidgetState(glanceIds, MealInfo.Unavailable)
            e.printStackTrace()
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
