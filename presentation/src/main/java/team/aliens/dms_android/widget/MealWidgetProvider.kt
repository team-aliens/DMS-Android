package team.aliens.dms_android.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import team.aliens.dms_android.feature.splash.SplashActivity
import team.aliens.presentation.R

class MealWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(
                context = context,
                appWidgetManager = appWidgetManager,
                appWidgetId = appWidgetId,
            )
        }
    }

    private fun updateAppWidget(
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

        val serviceIntent = Intent(context, MealService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }

        val servicePendingIntent =
            PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val view = RemoteViews(
            context.packageName,
            R.layout.widget_meal,
        ).apply {
            setRemoteAdapter(R.id.widget_meal, serviceIntent)
            setOnClickPendingIntent(R.id.widget_meal, pendingIntent)
        }

        context.startService(serviceIntent)

        appWidgetManager.updateAppWidget(appWidgetId, view)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_meal)
    }
}