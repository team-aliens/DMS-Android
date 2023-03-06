package team.aliens.dms_android.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import team.aliens.domain.usecase.meal.RemoteMealUseCase
import team.aliens.presentation.R
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class MealService : Service(), CoroutineScope by MainScope() {

    @Inject
    lateinit var remoteMealUseCase: RemoteMealUseCase

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        launch {
            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )

            val meal = getMealState()

            val remoteViews = RemoteViews(packageName, R.layout.widget_meal).apply {
                setTextViewText(R.id.str_meal, meal.meal)
                setTextViewText(R.id.str_kcal, meal.calories)
                setTextViewText(R.id.str_meal_title, meal.mealType.title)
                setImageViewResource(R.id.ic_meal, meal.mealType.icon.drawableId)
            }

            val appWidgetManager = AppWidgetManager.getInstance(this@MealService)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

            stopSelf(startId)
        }
        return START_NOT_STICKY
    }

    private suspend fun getMealState(): MealState {
        val nowDate = LocalDate.now()
        val nowDateTime = LocalDateTime.now()

        val result = remoteMealUseCase.execute(nowDate)
            .meals
            .first { it.date == LocalDate.now().toString() }

        val currentMealType = MealType.getCurrentMealType(nowDateTime)

        val meal = when (currentMealType) {
            MealType.Breakfast -> result.breakfast
            MealType.Lunch -> result.lunch
            MealType.Dinner -> result.dinner
        }

        return MealState(
            mealType = currentMealType,
            meal = meal.dropLast(1).joinToString("\n"),
            calories = meal.last()
        )
    }
}