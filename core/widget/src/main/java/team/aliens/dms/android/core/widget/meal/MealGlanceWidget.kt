package team.aliens.dms.android.core.widget.meal

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentWidth
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.widget.designsystem.WidgetTheme

class MealGlanceWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = MealInfoStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MealWidget()
        }
    }

    @Composable
    internal fun MealWidget() {
        val mealInfo = currentState<MealInfo>()

        GlanceTheme {
            when (mealInfo) {
                MealInfo.Loading -> Loading()

                is MealInfo.Unavailable -> Unavailable()

                is MealInfo.Available -> {
                    when (MealType.getCurrentMealType()) {
                        MealType.Breakfast -> {
                            MealBig(
                                mealState = MealState(
                                    mealType = MealType.Breakfast,
                                    meal = mealInfo.breakfast,
                                    calories = mealInfo.kcalOfBreakfast,
                                ),
                            )
                        }

                        MealType.Launch -> {
                            MealBig(
                                mealState = MealState(
                                    mealType = MealType.Launch,
                                    meal = mealInfo.lunch,
                                    calories = mealInfo.kcalOfLunch,
                                ),
                            )
                        }

                        MealType.Dinner -> {
                            MealBig(
                                mealState = MealState(
                                    mealType = MealType.Dinner,
                                    meal = mealInfo.dinner,
                                    calories = mealInfo.kcalOfDinner,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MealBig(
        mealState: MealState,
    ) {
        val context = LocalContext.current
        val intent = Intent().setClassName(
            context.packageName, "team.aliens.dms.android.app.MainActivity",
        )
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable {
                    pendingIntent.send()
                }
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp,
                )
                .background(DmsTheme.colorScheme.background),
        ) {
            Column(
                modifier = GlanceModifier.fillMaxHeight(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        provider = ImageProvider(mealState.mealType.icon),
                        contentDescription = null,
                    )
                    Spacer(GlanceModifier.width(4.dp))
                    Text(
                        text = mealState.mealType.title,
                        style = TextStyle(
                            color = WidgetTheme.colors.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
                Spacer(GlanceModifier.defaultWeight())
                Text(
                    text = mealState.calories,
                    style = TextStyle(
                        color = WidgetTheme.colors.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
            LazyColumn(
                modifier = GlanceModifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                items(mealState.meal) {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = WidgetTheme.colors.surfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "로딩중...",
            style = TextStyle(
                color = WidgetTheme.colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Composable
private fun Unavailable() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "급식을 불러오지 못했어요",
            style = TextStyle(
                color = WidgetTheme.colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}
