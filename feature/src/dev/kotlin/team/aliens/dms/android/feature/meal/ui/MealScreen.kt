package team.aliens.dms.android.feature.meal.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.DmsIconButton
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.core.designsystem.calendar.DmsCalendar
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.endPadding
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.meal.component.DateChip
import team.aliens.dms.android.feature.meal.component.MealContent
import team.aliens.dms.android.feature.meal.viewmodel.MealState
import team.aliens.dms.android.feature.meal.viewmodel.MealViewModel
import team.aliens.dms.android.feature.meal.viewmodel.getProperMeal
import team.aliens.dms.android.shared.date.util.now

const val MAX_CALENDAR_COUNT = 366

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Meal(
    onNavigateBack: () -> Unit,
) {
    val viewModel: MealViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val calendarBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pageCount = MAX_CALENDAR_COUNT
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount },
    )
    var previousPage by remember { mutableStateOf(pagerState.currentPage) }
    val (dailyMeals, kcal) = when (state.currentCardType) {
        MealCardType.BREAKFAST -> state.meal.breakfast to state.meal.kcalOfBreakfast
        MealCardType.LUNCH -> state.meal.lunch to state.meal.kcalOfLunch
        MealCardType.DINNER -> state.meal.dinner to state.meal.kcalOfDinner
    }
    val mealCardGradientColors = when (state.currentCardType) {
        MealCardType.BREAKFAST -> listOf(Color(0xFF0F6EFE), Color(0xFFFFCB52))
        MealCardType.LUNCH -> listOf(Color(0xFF0F6EFE), Color(0xFFFFFFFF))
        MealCardType.DINNER -> listOf(Color(0xFF7A3BA1), Color(0xFFFFFFFF))
    }
    val backgroundGradient = Brush.verticalGradient(mealCardGradientColors)
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage > previousPage) {
            viewModel.setNextMealType()
        }
        if (pagerState.currentPage < previousPage) {
            viewModel.setPreviousMealType()
        }
        previousPage = pagerState.currentPage
    }

    if (state.isShowCalendar) {
        ModalBottomSheet(
            sheetState = calendarBottomSheetState,
            containerColor = DmsTheme.colorScheme.surfaceTint,
            onDismissRequest = viewModel::hideCalendarBottomSheet,
        ) {
            DmsCalendar(
                modifier = Modifier.fillMaxWidth(),
                selectDate = state.selectedDate,
                onSelectedDateChange = viewModel::setDate,
            )
        }
    }
    MealScreen(
        state = state,
        pagerState = pagerState,
        currentCardType = state.currentCardType,
        kcal = kcal,
        dailyMeals = dailyMeals.toPersistentList(),
        backgroundGradient = backgroundGradient,
        onBackClick = onNavigateBack,
        onCalendarClick = viewModel::showCalendarBottomSheet,
        onPreviousClick = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        },
        onNextClick = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    )
}

@Composable
private fun MealScreen(
    state: MealState,
    currentCardType: MealCardType,
    kcal: String?,
    dailyMeals: ImmutableList<String>,
    backgroundGradient: Brush,
    pagerState: PagerState,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackClick,
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Canvas(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .topPadding(70.dp)
                    .size(300.dp)
                    .blur(radius = 120.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                onDraw = {
                    drawCircle(backgroundGradient)
                },
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DateChip(date = state.selectedDate)

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = pagerState,
                    ) { page ->
                        val mealPage = page + getProperMeal().ordinal
                        val cardType = when (mealPage % 3) {
                            0 -> MealCardType.BREAKFAST
                            1 -> MealCardType.LUNCH
                            else -> MealCardType.DINNER
                        }
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp)
                                .align(Alignment.Center),
                            painter = painterResource(cardType.iconRes),
                            contentDescription = null,
                        )
                    }
                    DmsIconButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .startPadding(24.dp),
                        resource = DmsIcon.Backward,
                        tint = DmsTheme.colorScheme.scrim,
                        size = 34.dp,
                        onClick = onPreviousClick,
                    )
                    DmsIconButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .endPadding(24.dp)
                            .rotate(180f),
                        resource = DmsIcon.Backward,
                        tint = DmsTheme.colorScheme.scrim,
                        size = 34.dp,
                        onClick = onNextClick,
                    )
                }
                MealContent(
                    modifier = Modifier
                        .topPadding(20.dp)
                        .horizontalPadding(10.dp),
                    daily = currentCardType.title,
                    kcal = kcal,
                    meal = dailyMeals,
                )
                DmsItemButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topPadding(20.dp)
                        .horizontalPadding(10.dp),
                    iconRes = painterResource(R.drawable.img_calendar),
                    text = "급식 캘린더 보기",
                    onClick = onCalendarClick,
                )
            }
        }
    }
}

internal enum class MealCardType(
    val title: String,
    @DrawableRes val iconRes: Int,
) {
    BREAKFAST(
        title = "아침",
        iconRes = R.drawable.img_morning,
    ),
    LUNCH(
        title = "점심",
        iconRes = R.drawable.img_launch,
    ),
    DINNER(
        title = "저녁",
        iconRes = R.drawable.img_dinner,
    ),
}

