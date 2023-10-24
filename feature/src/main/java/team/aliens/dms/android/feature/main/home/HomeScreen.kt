package team.aliens.dms.android.feature.main.home

import android.content.Context
import android.os.Vibrator
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms.android.feature.main.home.MealCardType.BREAKFAST
import team.aliens.dms.android.feature.main.home.MealCardType.DINNER
import team.aliens.dms.android.feature.main.home.MealCardType.LUNCH
import team.aliens.dms.android.shared.date.util.now
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onShowCalendar: () -> Unit,
    selectedCalendarDate: LocalDate,
    onSelectedCalendarDateChange: (newDate: LocalDate) -> Unit,
    onNavigateToAnnouncementList: () -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            else -> {}
        }
    }

    LaunchedEffect(selectedCalendarDate) {
        viewModel.postIntent(HomeIntent.UpdateSelectedDate(selectedDate = selectedCalendarDate))
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    AppLogo(
                        modifier = Modifier.size(
                            width = 77.dp,
                            height = 28.dp,
                        ),
                    )
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .padding(padValues),
        ) {
            AnnouncementCard(
                visible = uiState.newNoticesExist,
                onNavigateToAnnouncementList = onNavigateToAnnouncementList,
            )
            Spacer(modifier = Modifier.weight(2f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.meal_todays_meal),
                textAlign = TextAlign.Center,
                style = DmsTheme.typography.title1,
                color = DmsTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.weight(2f))
            DateCard(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = selectedCalendarDate,
                onNextDay = { /*TODO*/ },
                onPreviousDay = { /*TODO*/ },
                onShowCalendar = onShowCalendar,
            )
            Spacer(modifier = Modifier.weight(1f))
            MealCards(
                modifier = Modifier.weight(20f),
                currentDate = selectedCalendarDate,
                breakfast = uiState.mealOfDate?.breakfast
                    ?: emptyList(), // TODO: make viewmodel handle empty list
                kcalOfBreakfast = uiState.mealOfDate?.kcalOfBreakfast
                    ?: stringResource(R.string.meal_not_exists),
                lunch = uiState.mealOfDate?.lunch ?: emptyList(),
                kcalOfLunch = uiState.mealOfDate?.kcalOfLunch
                    ?: stringResource(R.string.meal_not_exists),
                dinner = uiState.mealOfDate?.dinner ?: emptyList(),
                kcalOfDinner = uiState.mealOfDate?.kcalOfDinner
                    ?: stringResource(R.string.meal_not_exists),
                onNextDay = { onSelectedCalendarDateChange(selectedCalendarDate.plusDays(1)) },
                onPreviousDay = { onSelectedCalendarDateChange(selectedCalendarDate.minusDays(1)) },
            )
            Spacer(modifier = Modifier.weight(5f))
        }
    }
}

@Composable
private fun AnnouncementCard(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onNavigateToAnnouncementList: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut(),
    ) {
        Box(
            modifier = Modifier.horizontalPadding(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            FloatingNotice(text = stringResource(R.string.notice_new_notice_exists))
            Image(
                modifier = Modifier
                    .endPadding(value = PaddingDefaults.Small)
                    .size(32.dp)
                    .clip(DmsTheme.shapes.circle)
                    .clickable(onClick = onNavigateToAnnouncementList),
                painter = painterResource(R.drawable.ic_next),
                contentDescription = stringResource(R.string.notice),
            )
        }
    }
}

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    onShowCalendar: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = DefaultHorizontalSpace,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        CalendarArrow(
            type = ArrowType.BACKWARD,
            onClick = onPreviousDay,
        )
        OutlinedButton(
            onClick = onShowCalendar,
            contentPadding = PaddingValues(
                horizontal = PaddingDefaults.Small,
                vertical = PaddingDefaults.ExtraSmall,
            ),
            fillMinSize = false,
            colors = ButtonDefaults.outlinedGrayButtonColors(),
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_calendar),
                tint = DmsTheme.colorScheme.onSurface,
                contentDescription = null,
            )
            Text(
                text = "$selectedDate (${selectedDate.dayOfWeek.text})",
                style = DmsTheme.typography.button,
                color = DmsTheme.colorScheme.onSurface,
            )
        }
        CalendarArrow(
            type = ArrowType.FORWARD,
            onClick = onNextDay,
        )
    }
}

private val DayOfWeek.text: String
    @Composable inline get() = stringResource(
        when (this) {
            DayOfWeek.SUNDAY -> R.string.sunday_abb
            DayOfWeek.MONDAY -> R.string.monday_abb
            DayOfWeek.TUESDAY -> R.string.tuesday_abb
            DayOfWeek.WEDNESDAY -> R.string.wednesday_abb
            DayOfWeek.THURSDAY -> R.string.thursday_abb
            DayOfWeek.FRIDAY -> R.string.friday_abb
            DayOfWeek.SATURDAY -> R.string.saturday_abb
            else -> throw IllegalArgumentException()
        },
    )

@Composable
private fun CalendarArrow(
    modifier: Modifier = Modifier,
    type: ArrowType,
    onClick: () -> Unit,
) {
    Image(
        modifier = modifier
            .padding(PaddingDefaults.ExtraSmall)
            .size(28.dp)
            .clip(DmsTheme.shapes.medium)
            .clickable(onClick = onClick),
        painter = painterResource(id = type.iconRes),
        contentDescription = null,
    )
}

@Immutable
private enum class ArrowType(
    @DrawableRes val iconRes: Int,
) {
    BACKWARD(
        iconRes = team.aliens.dms.android.core.designsystem.R.drawable.ic_backward,
    ),
    FORWARD(
        iconRes = team.aliens.dms.android.core.designsystem.R.drawable.ic_forward,
    ),
    ;
}

// TODO __-----------------------------

@Immutable
private enum class MealCardType(
    @DrawableRes val iconRes: Int,
) {
    BREAKFAST(
        iconRes = team.aliens.dms.android.core.designsystem.R.drawable.ic_breakfast,
    ),
    LUNCH(
        iconRes = team.aliens.dms.android.core.designsystem.R.drawable.ic_lunch,
    ),
    DINNER(
        iconRes = team.aliens.dms.android.core.designsystem.R.drawable.ic_dinner,
    ),
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
private fun MealCards(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
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
        pageCount = { MealCardType.entries.size },
    )

    val scope = rememberCoroutineScope()
    var firstEnter by remember(currentDate) { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(pagerState.currentPage) {
        if (firstEnter) firstEnter = false
        else vibrateOnMealCardPaging(context)
    }

    HorizontalPager(
        modifier = modifier,
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
                .padding(8.dp),
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

private const val BreakfastStartTime: Int = 9
private const val LunchStartTime: Int = 13
private const val DinnerStartTime: Int = 19

private fun getProperMeal(): Int = when (now.hour) {
    in BreakfastStartTime until LunchStartTime -> Lunch
    in LunchStartTime until DinnerStartTime -> Dinner
    else -> Breakfast
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

    OutlinedCard(
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
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DmsTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            when (currentCardType) {
                BREAKFAST -> Dishes(
                    iconRes = BREAKFAST.iconRes,
                    dishes = breakfast,
                    kcal = kcalOfBreakfast,
                )

                LUNCH -> Dishes(
                    iconRes = LUNCH.iconRes,
                    dishes = lunch,
                    kcal = kcalOfLunch,
                )

                DINNER -> Dishes(
                    iconRes = DINNER.iconRes,
                    dishes = dinner,
                    kcal = kcalOfDinner,
                )
            }
        }
    }
}

@Composable
private fun Dishes(
    iconRes: Int,
    dishes: List<String>,
    kcal: String,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = DmsTheme.colorScheme.primary,
            )
        }
        items(dishes) { menu ->
            Text(
                text = menu,
                textAlign = TextAlign.Center,
            )
        }
        item {
            Text(
                text = kcal,
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private enum class DragDirection {
    LEFT, RIGHT,
    ;
}

@Suppress("DEPRECATION")
private fun vibrateOnMealCardPaging(
    context: Context,
) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(3L)
}
