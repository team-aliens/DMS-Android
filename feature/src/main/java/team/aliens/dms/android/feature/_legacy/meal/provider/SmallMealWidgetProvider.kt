package team.aliens.dms.android.feature._legacy.meal.provider
/*

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.splash.SplashActivity
import team.aliens.dms_android.widget.meal.MealService
import team.aliens.presentation.R

class SmallMealWidgetProvider : BaseMealWidgetProvider(), CoroutineScope by MainScope() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        launch {
            appWidgetIds.forEach { appWidgetId ->
                updateAppWidget(
                    context = context,
                    appWidgetManager = appWidgetManager,
                    appWidgetId = appWidgetId,
                )
            }
        }
    }

    private suspend fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, SplashActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )

        val mealState = getMealState(context)

        val view = RemoteViews(
            context.packageName,
            R.layout.small_widget_meal,
        ).apply {
            setOnClickPendingIntent(R.id.small_widget_meal, pendingIntent)
            setTextViewText(R.id.small_str_meal, mealState.meal)
            setImageViewResource(R.id.small_ic_meal, mealState.mealType.icon.drawableId)
        }

        appWidgetManager.apply {
            updateAppWidget(appWidgetId, view)
            notifyAppWidgetViewDataChanged(appWidgetId, R.id.small_str_meal)
        }
    }
}*/
