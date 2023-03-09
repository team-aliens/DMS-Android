package team.aliens.dms_android.widget.meal.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.splash.SplashActivity
import team.aliens.presentation.R


class BigMealWidgetProvider : BaseMealWidgetProvider(), CoroutineScope by MainScope() {

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

        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.big_widget_meal,
        ).apply {
            setOnClickPendingIntent(R.id.widget_meal, pendingIntent)
            setTextViewText(R.id.str_meal, mealState.meal)
            setTextViewText(R.id.str_kcal, mealState.calories)
            setTextViewText(R.id.str_meal_title, mealState.mealType.title)
            setImageViewResource(R.id.ic_meal, mealState.mealType.icon.drawableId)
        }

        appWidgetManager.apply {
            updateAppWidget(appWidgetId, remoteViews)
            notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_meal)
        }
    }
}