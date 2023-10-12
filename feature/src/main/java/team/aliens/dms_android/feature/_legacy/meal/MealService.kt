package team.aliens.dms_android.feature._legacy.meal
/*

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import team.aliens.dms_android.constans.Extra
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.domain.usecase.meal.FetchMealsUseCase
import team.aliens.presentation.R
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

*/
/**
 * FIXME
 *
 * 백그라운드 제한 정책에 의해 사용할 수 없음
 * WorkManager 로 대체 요함
 *//*

@AndroidEntryPoint
class MealService : Service(), CoroutineScope by MainScope() {

    @Inject
    lateinit var fetchMealsUseCase: FetchMealsUseCase

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        launch {
            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID,
            )

            val isSizeBig = intent.getBooleanExtra(Extra.isMealSizeBig, true)

            val meal = getMealState()

            val remoteViews = if (isSizeBig) {
                RemoteViews(packageName, R.layout.big_widget_meal).apply {
                    setTextViewText(R.id.str_meal, meal.meal)
                    setTextViewText(R.id.str_kcal, meal.calories)
                    setTextViewText(R.id.str_meal_title, meal.mealType.title)
                    setImageViewResource(R.id.ic_meal, meal.mealType.icon.drawableId)
                }
            } else {
                RemoteViews(packageName, R.layout.small_widget_meal).apply {
                    setTextViewText(R.id.small_str_meal, meal.meal)
                    setImageViewResource(R.id.small_ic_meal, meal.mealType.icon.drawableId)
                }
            }

            val appWidgetManager = AppWidgetManager.getInstance(this@MealService)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

            stopSelf(startId)
        }
        return START_NOT_STICKY
    }

    private suspend fun getMealState(): MealState {
        val nowDate = LocalDate.now().toString()
        val nowDateTime = LocalDateTime.now()

        var mealEntity = FetchMealsOutput.MealInformation(
            date = LocalDate.now().toString(),
            breakfast = listOf(applicationContext.getString(R.string.MealNotFound)),
            lunch = listOf(applicationContext.getString(R.string.MealNotFound)),
            dinner = listOf(applicationContext.getString(R.string.MealNotFound)),
        )

        kotlin.runCatching {
            fetchMealsUseCase(
                fetchMealsInput = FetchMealsInput(
                    date = nowDate,
                ),
            )
        }.onSuccess { result ->
            mealEntity = result.meals.first { it.date == nowDateTime.toString() }
            //.first { it.date == LocalDate.now().toString() }
        }

        val currentMealType = MealType.getCurrentMealType(nowDateTime)

        val meal = when (currentMealType) {
            MealType.Breakfast -> mealEntity.breakfast
            MealType.Lunch -> mealEntity.lunch
            MealType.Dinner -> mealEntity.dinner
        }

        val mealNotFound: Boolean = meal.size > 1

        return MealState(
            mealType = currentMealType,
            meal = if (mealNotFound) meal.dropLast(1)
                .joinToString("\n") else applicationContext.getString(R.string.MealNotFound),
            calories = if (mealNotFound) meal.last() else ""
        )
    }
}*/
