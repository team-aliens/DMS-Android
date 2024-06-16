package team.aliens.dms.android.core.widget.meal

import android.content.Context
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent

class MealGlanceWidget : GlanceAppWidget() {

    companion object {
        private val smallMode = DpSize(120.dp, 120.dp)
        private val bigMode = DpSize(180.dp, 180.dp)
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MealWidget()
        }
    }

    override val sizeMode: SizeMode
        get() = SizeMode.Responsive(setOf(smallMode, bigMode))
}