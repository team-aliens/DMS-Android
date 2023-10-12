@file:Suppress("ConstPropertyName")

package team.aliens.dms_android.feature.home

import android.content.Context
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import team.aliens.dms_android.design_system.color.DormColor
import team.aliens.dms_android.design_system.icon.DormIcon
import team.aliens.dms_android.design_system.modifier.dormClickable
import team.aliens.dms_android.design_system.modifier.dormGradientBackground
import team.aliens.dms_android.design_system.modifier.dormShadow
import team.aliens.dms_android.design_system.theme.DormTheme
import team.aliens.dms_android.design_system.typography.Body2
import team.aliens.dms_android.design_system.typography.Body5
import team.aliens.dms_android.design_system.typography.Caption
import team.aliens.dms_android.design_system.typography.Title1
import team.aliens.dms_android.feature._legacy.AppLogo
import team.aliens.dms_android.feature._legacy.FloatingNotice
import team.aliens.dms_android.feature._legacy.listFadeBrush
import team.aliens.dms_android.feature.home.MealCardType.BREAKFAST
import team.aliens.dms_android.feature.home.MealCardType.DINNER
import team.aliens.dms_android.feature.home.MealCardType.LUNCH
import team.aliens.dms_android.feature.R
import java.util.Calendar
import java.util.Date
import kotlin.math.absoluteValue

@Stable
private val defaultBackgroundBrush = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Transparent,
        DormColor.DormPrimary.copy(
            alpha = 0.1f,
        ),
    ),
)

@Destination
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    calendarDate: Date,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    onShowCalendar: () -> Unit,
    onNavigateToNoticeScreen: () -> Unit,
    onNavigateToNotificationBox: () -> Unit,
) {
    val state by homeViewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(calendarDate) { homeViewModel.postIntent(HomeIntent.UpdateDate(calendarDate)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .dormGradientBackground(defaultBackgroundBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeScreenAppLogo(
            onNavigateToNotificationBox = onNavigateToNotificationBox,
        )
        NoticeCard(
            visible = state.newNotices,
            onIconClicked = onNavigateToNoticeScreen,
        )
        Column(
            modifier = Modifier.animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.weight(0.05f))
            Title1(
                text = stringResource(R.string.meal_todays_meal),
            )
            Spacer(Modifier.weight(0.05f))
            DateCard(
                selectedDate = state.selectedDate,
                onNextDay = onNextDay,
                onPreviousDay = onPreviousDay,
                onShowCalendar = onShowCalendar,
            )
            Spacer(Modifier.weight(0.1f))
            MealCards(
                currentDate = calendarDate,
                breakfast = state.breakfast,
                kcalOfBreakfast = state.kcalOfBreakfast
                    ?: stringResource(R.string.meal_not_exists),
                lunch = state.lunch,
                kcalOfLunch = state.kcalOfLunch ?: stringResource(R.string.meal_not_exists),
                dinner = state.dinner,
                kcalOfDinner = state.kcalOfDinner ?: stringResource(R.string.meal_not_exists),
                onNextDay = onNextDay,
                onPreviousDay = onPreviousDay,
            )
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun HomeScreenAppLogo(
    modifier: Modifier = Modifier,
    onNavigateToNotificationBox: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppLogo()
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clip(RoundedCornerShape(10.dp))
                .dormClickable(onClick = onNavigateToNotificationBox),
            painter = painterResource(DormIcon.Bell.drawableId),
            contentDescription = null,
            tint = DormTheme.colors.primaryVariant,
        )
    }
}

@Composable
private fun ColumnScope.NoticeCard(
    visible: Boolean,
    onIconClicked: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + fadeIn(),
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            Box(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 16.dp,
                ),
                contentAlignment = Alignment.CenterEnd,
            ) {
                FloatingNotice(
                    text = stringResource(R.string.notice_new_notice_exists),
                )
                Image(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(32.dp)
                        .dormClickable(
                            rippleEnabled = false,
                            onClick = onIconClicked,
                        ),
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = stringResource(R.string.notice),
                )
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .dormGradientBackground(listFadeBrush),
            )
        }
    }
}

@Composable
private fun DateCard(
    selectedDate: Date,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    onShowCalendar: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        CalendarArrow(
            type = ArrowType.BACKWARD,
            onClick = onPreviousDay,
        )
        DateTextButton(
            selectedDate = selectedDate,
            onShowCalendar = onShowCalendar,
        )
        CalendarArrow(
            type = ArrowType.FORWARD,
            onClick = onNextDay,
        )
    }
}

@Composable
private fun CalendarArrow(
    type: ArrowType,
    onClick: () -> Unit,
) {
    Image(
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
            .clip(
                RoundedCornerShape(8.dp),
            )
            .dormClickable(onClick = onClick),
        painter = painterResource(type.icon.drawableId),
        contentDescription = null,
    )
}

@Composable
private fun DateTextButton(
    selectedDate: Date,
    onShowCalendar: () -> Unit,
) {
    Row(
        modifier = Modifier
            .dormShadow(DormTheme.colors.primaryVariant)
            .background(
                color = DormTheme.colors.surface,
                shape = RoundedCornerShape(5.dp),
            )
            .border(
                width = 1.dp,
                color = DormTheme.colors.primaryVariant,
                shape = RoundedCornerShape(5.dp),
            )
            .clip(
                shape = RoundedCornerShape(5.dp),
            )
            .dormClickable(onClick = onShowCalendar)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
        )
        Body5(
            text = "${selectedDate.toMealFormattedString()} (${selectedDate.getDayOfWeek()})",
        )
    }
}

@Composable
private fun Date.getDayOfWeek(): String {
    val digit = this.getDigitOfDayOfWeek()
    return stringResource(
        when (digit) {
            Calendar.SUNDAY -> R.string.sunday_abb
            Calendar.MONDAY -> R.string.monday_abb
            Calendar.TUESDAY -> R.string.tuesday_abb
            Calendar.WEDNESDAY -> R.string.wednesday_abb
            Calendar.THURSDAY -> R.string.thursday_abb
            Calendar.FRIDAY -> R.string.friday_abb
            Calendar.SATURDAY -> R.string.saturday_abb
            else -> throw IllegalArgumentException()
        },
    )
}

private fun Date.getDigitOfDayOfWeek(): Int {
    val calendar = Calendar.getInstance().apply {
        time = this@getDigitOfDayOfWeek
    }
    return calendar.get(Calendar.DAY_OF_WEEK)
}

@Immutable
private enum class ArrowType(
    val icon: DormIcon,
) {
    BACKWARD(DormIcon.Backward), FORWARD(DormIcon.Forward),
    ;
}

@Immutable
private enum class MealCardType(
    val icon: DormIcon,
) {
    BREAKFAST(DormIcon.Breakfast), LUNCH(DormIcon.Lunch), DINNER(DormIcon.Dinner),
    ;
}

private fun Int.asMealCardType(): MealCardType {
    return when (this) {
        0 -> BREAKFAST
        1 -> LUNCH
        2 -> DINNER
        else -> throw IllegalArgumentException()
    }
}

private const val Breakfast = 0
private const val Lunch = 1
private const val Dinner = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.MealCards(
    currentDate: Date,
    breakfast: List<String>,
    kcalOfBreakfast: String,
    lunch: List<String>,
    kcalOfLunch: String,
    dinner: List<String>,
    kcalOfDinner: String,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = getProperMeal(),
        pageCount = { MealCardType.values().size },
    )

    val scope = rememberCoroutineScope()
    var firstEnter by remember(currentDate) { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(pagerState.currentPage) {
        if (firstEnter) firstEnter = false
        else vibrateOnMealCardPaging(context)
    }

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f),
        state = pagerState,
        contentPadding = PaddingValues(
            horizontal = 56.dp,
        ),
        userScrollEnabled = false,
    ) { page ->
        val currentCardType = page.asMealCardType()

        MealCard(
            modifier = Modifier
                .graphicsLayer {
                    val pagerOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    lerp(
                        start = 0.875f, stop = 1f, fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pagerOffset.coerceIn(0f, 1f),
                    )
                }
                .padding(8.dp)
                .dormShadow(color = DormTheme.colors.primaryVariant),
            currentCardType = currentCardType,
            breakfast = breakfast,
            kcalOfBreakfast = kcalOfBreakfast,
            lunch = lunch,
            kcalOfLunch = kcalOfLunch,
            dinner = dinner,
            kcalOfDinner = kcalOfDinner,
            onSwipeToRight = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        when (page) {
                            BREAKFAST.ordinal -> LUNCH.ordinal
                            LUNCH.ordinal -> DINNER.ordinal
                            DINNER.ordinal -> BREAKFAST.ordinal.also { onNextDay() }
                            else -> throw IllegalStateException()
                        },
                    )
                }
            },
            onSwipeToLeft = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        when (page) {
                            BREAKFAST.ordinal -> DINNER.ordinal.also { onPreviousDay() }
                            LUNCH.ordinal -> BREAKFAST.ordinal
                            DINNER.ordinal -> LUNCH.ordinal
                            else -> throw IllegalStateException()
                        },
                    )
                }
            },
        )
    }
}

private enum class DragDirection {
    LEFT, RIGHT,
    ;
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    currentCardType: MealCardType,
    breakfast: List<String>,
    kcalOfBreakfast: String,
    lunch: List<String>,
    kcalOfLunch: String,
    dinner: List<String>,
    kcalOfDinner: String,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
) {
    var dragDirection: DragDirection? by remember { mutableStateOf(null) }

    Card(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(currentCardType) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when (dragDirection) {
                            DragDirection.LEFT -> onSwipeToLeft()
                            DragDirection.RIGHT -> onSwipeToRight()
                            null -> { /* explicit blank */
                            }
                        }
                    },
                ) { _, dragAmount ->
                    if (currentCardType == LUNCH) when {
                        dragAmount > 0 -> onSwipeToLeft()
                        dragAmount < 0 -> onSwipeToRight()
                    } else when {
                        dragAmount > 0 -> dragDirection = DragDirection.LEFT
                        dragAmount < 0 -> dragDirection = DragDirection.RIGHT
                    }
                }
            }
            .padding(
                horizontal = 8.dp,
            )
            .background(
                color = DormTheme.colors.background,
                shape = RoundedCornerShape(20.dp),
            )
            .clip(
                RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = DormTheme.colors.primary,
                shape = RoundedCornerShape(20.dp),
            ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            when (currentCardType) {
                BREAKFAST -> Dishes(
                    icon = BREAKFAST.icon,
                    dishes = breakfast,
                    kcal = kcalOfBreakfast,
                )

                LUNCH -> Dishes(
                    icon = LUNCH.icon,
                    dishes = lunch,
                    kcal = kcalOfLunch,
                )

                DINNER -> Dishes(
                    icon = DINNER.icon,
                    dishes = dinner,
                    kcal = kcalOfDinner,
                )
            }
        }
    }
}

@Suppress("DEPRECATION")
private fun vibrateOnMealCardPaging(
    context: Context,
) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(3L)
}

@Composable
private fun Dishes(
    icon: DormIcon,
    dishes: List<String>,
    kcal: String,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Icon(
                painter = painterResource(
                    id = icon.drawableId,
                ),
                contentDescription = null,
                tint = DormTheme.colors.primaryVariant,
            )
        }
        items(dishes) { menu ->
            Body2(
                text = menu,
                textAlign = TextAlign.Center,
            )
        }
        item {
            Caption(
                text = kcal,
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}

private const val BreakfastStartTime: Int = 9
private const val LunchStartTime: Int = 13
private const val DinnerStartTime: Int = 19

private fun getProperMeal(): Int {
    val calendar = Calendar.getInstance().apply {
        time = Date()
    }
    return when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in BreakfastStartTime until LunchStartTime -> Lunch
        in LunchStartTime until DinnerStartTime -> Dinner
        else -> Breakfast
    }
}
