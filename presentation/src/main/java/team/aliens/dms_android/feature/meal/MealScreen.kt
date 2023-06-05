@file:Suppress("ConstPropertyName")

package team.aliens.dms_android.feature.home.meal

import android.content.Context
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.DormCalendar
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.presentation.R
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MealScreen(
    onNavigateToNoticeScreen: () -> Unit,
    mealViewModel: MealViewModel = hiltViewModel(),
) {
    val uiState by mealViewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val calendarSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
    )

    val onCalendarDateChange = { date: Date ->
        mealViewModel.onEvent(MealUiEvent.UpdateDate(date))
    }

    val onNextDay = {
        mealViewModel.onEvent(MealUiEvent.UpdateDateToNextDay)
    }
    val onPreviousDay = {
        mealViewModel.onEvent(MealUiEvent.UpdateDateToPreviousDay)
    }
    val onShowCalendar: () -> Unit = {
        coroutineScope.launch {
            calendarSheetState.show()
        }
    }

    DormCalendar(
        bottomSheetState = calendarSheetState,
        onDateChange = onCalendarDateChange,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .dormGradientBackground(defaultBackgroundBrush),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MealScreenAppLogo()
            NoticeCard(
                visible = uiState.newNotices,
                onIconClicked = onNavigateToNoticeScreen,
            )
            Spacer(Modifier.height(20.dp))
            Title1(
                text = stringResource(R.string.meal_todays_meal),
            )
            Spacer(Modifier.height(46.dp))
            DateCard(
                selectedDate = uiState.selectedDate,
                onNextDay = onNextDay,
                onPreviousDay = onPreviousDay,
                onShowCalendar = onShowCalendar,
            )
            Spacer(Modifier.height(36.dp))
            DishCards(
                breakfast = uiState.breakfast,
                kcalOfBreakfast = uiState.kcalOfBreakfast
                    ?: stringResource(R.string.meal_not_exists),
                lunch = uiState.lunch,
                kcalOfLunch = uiState.kcalOfLunch ?: stringResource(R.string.meal_not_exists),
                dinner = uiState.dinner,
                kcalOfDinner = uiState.kcalOfDinner ?: stringResource(R.string.meal_not_exists),
                onNextDay = onNextDay,
                onPreviousDay = onPreviousDay,
            )
            Spacer(Modifier.height(120.dp))
        }
    }
}

@Composable
private fun MealScreenAppLogo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
    }
}

@Composable
private fun NoticeCard(
    visible: Boolean,
    onIconClicked: () -> Unit,
) {

    // FIXME replace with fade brush
    val color = DormTheme.colors
    val pointFadedBackgroundBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                color.background,
                Color.Transparent,
            ),
        )
    } // FIXME end

    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(),
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.padding(
                    top = 18.dp,
                    bottom = 18.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            ) {
                FloatingNotice(
                    content = stringResource(R.string.notice_new_notice_exists),
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
                    .dormGradientBackground(pointFadedBackgroundBrush),
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

@Stable
private enum class ArrowType(
    val icon: DormIcon,
) {
    BACKWARD(DormIcon.Backward), FORWARD(DormIcon.Forward), ;
}

private const val Breakfast = 0
private const val Lunch = 1
private const val Dinner = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.DishCards(
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
    )

    val scope = rememberCoroutineScope()
    var firstEnter by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(pagerState.currentPage) {
        if (firstEnter) firstEnter = false
        else vibrateOnMealCardPaging(context)
    }

    HorizontalPager(
        modifier = Modifier.weight(1f),
        pageCount = 3,
        state = pagerState,
        contentPadding = PaddingValues(
            horizontal = 64.dp,
        ),
        userScrollEnabled = false,
    ) { page ->
        MealCard(
            modifier = Modifier.graphicsLayer {
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
            },
            currentPage = pagerState.currentPage,
            page = page,
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
                            Breakfast -> Lunch
                            Lunch -> Dinner
                            Dinner -> run {
                                onNextDay()
                                Breakfast
                            }

                            else -> throw IllegalStateException()
                        },
                    )
                }
            },
            onSwipeToLeft = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        when (page) {
                            Breakfast -> run {
                                onPreviousDay()
                                Dinner
                            }

                            Lunch -> Breakfast
                            Dinner -> Lunch
                            else -> throw IllegalStateException()
                        },
                    )
                }
            },
        )
    }
}

private enum class DragDirection {
    LEFT, RIGHT, ;
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    currentPage: Int,
    page: Int,
    breakfast: List<String>,
    kcalOfBreakfast: String,
    lunch: List<String>,
    kcalOfLunch: String,
    dinner: List<String>,
    kcalOfDinner: String,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
) {
    var dragDirection: DragDirection? by remember {
        mutableStateOf(null)
    }
    Card(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when (dragDirection) {
                            DragDirection.LEFT -> onSwipeToLeft()
                            DragDirection.RIGHT -> onSwipeToRight()
                            null -> {/* explicit blank */
                            }
                        }
                    },
                ) { _, dragAmount ->
                    if (currentPage == Lunch) when {
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
            when (page) {
                Breakfast -> Dishes(
                    icon = DormIcon.Breakfast,
                    dishes = breakfast,
                    kcal = kcalOfBreakfast,
                )

                Lunch -> Dishes(
                    icon = DormIcon.Lunch,
                    dishes = lunch,
                    kcal = kcalOfLunch,
                )

                Dinner -> Dishes(
                    icon = DormIcon.Dinner,
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
        modifier = Modifier.fillMaxSize(),
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
