@file:Suppress("ConstPropertyName")

package team.aliens.dms_android.feature.meal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

/*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.DormCalendar
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.feature.notice.NoticeViewModel
import team.aliens.presentation.R
import java.time.DayOfWeek
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import team.aliens.design_system.extension.Space
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra

private val RainbowBlue = Color(0xFF2196F3)
private val RainbowIndigo = Color(0xFF3F51B5)
private val RainbowViolet = Color(0xFF9C27B0)
private val RainbowRed = Color(0xFFDA034E)
private val RainbowOrange = Color(0xFFFF9800)
private val RainbowYellow = Color(0xFFFFEB3B)
private val RainbowGreen = Color(0xFF4CAF50)
private val RainbowCyan = Color(0xFF4CA0AF)
private val RainbowLightBlue = Color(0xFF63B5F7)

@Stable
private val Rainbow = listOf(
    RainbowBlue,
    RainbowIndigo,
    RainbowViolet,
    RainbowRed,
    RainbowOrange,
    RainbowYellow,
    RainbowGreen,
    RainbowCyan,
    RainbowLightBlue,
)

@Stable
private val interval = 20000 / Rainbow.size


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CafeteriaScreen(
    navController: NavHostController,
    mealViewModel: MealViewModel = hiltViewModel(),
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    onMoveToNotice: () -> Unit,
) {

    *//*var backgroundGradient by rememberSaveable() {
        mutableStateOf(true)
    }

    val gradientBackgroundBrush: Brush? = if (backgroundGradient) {

        val infiniteTransition = rememberInfiniteTransition()

        val color by infiniteTransition.animateColor(
            initialValue = Rainbow[0],
            targetValue = Rainbow.last(),
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 20000
                    delayMillis = 10 * interval / 2
                    var i = 0
                    // set the keyframes from the rainbow with code
                    for (color in Rainbow) { // this is the crux  of setting the keyframes
                        color at i // at is an infix method in the KeyframesSpec class
                        i += interval
                    }
                },
                repeatMode = RepeatMode.Restart,
            ),
        )

        val finalColor = color.copy(
            color.alpha.minus(0.9f),
        )

        Brush.verticalGradient(
            listOf(
                DormTheme.colors.background,
                finalColor,
            ),
        )
    } else {
        null
    }*//*

    val isMealServiceEnabled = LocalAvailableFeatures.current[Extra.isMealServiceEnabled]

    val defaultBackgroundBrush: Brush by remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Transparent,
                    DormColor.DormPrimary.copy(
                        alpha = 0.1f,
                    ),
                ),
            ),
        )
    }

    val scope = rememberCoroutineScope()

    val state = noticeViewModel.state.collectAsState().value

    val calendarBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
    )

    val onCalendarClick = {
        scope.launch {
            calendarBottomSheetState.show()
        }
    }

    val onCalendarDateChange = { date: String ->
        mealViewModel.updateDay(
            date.toMealDateFormat(),
        )
    }

    LaunchedEffect(Unit) {
        noticeViewModel.checkNewNotice()
    }


    DormCalendar(
        bottomSheetState = calendarBottomSheetState,
        onChangeDate = onCalendarDateChange,
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .dormGradientBackground(
                        defaultBackgroundBrush,
                    ),
            ) {

                Space(space = 20.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {

                    AppLogo(
                        darkIcon = isSystemInDarkTheme(),
                    )

                    Space(ratio = 1f)

                    *//*Icon(
                        painter = painterResource(
                            DormIcon.Palette.drawableId,
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                32.dp,
                            )
                            .dormClickable {
                                backgroundGradient = !backgroundGradient
                            },
                        tint = if (backgroundGradient) {
                            DormTheme.colors.primary
                        } else {
                            DormTheme.colors.primaryVariant
                        },
                    )*//*
                }

                AnimatedVisibility(
                    modifier = Modifier.padding(
                        top = 36.dp,
                    ),
                    visible = state.hasNewNotice,
                ) {
                    ImportantNotice(
                        onNoticeIconClick = onMoveToNotice,
                    )
                }

                DateSelector(
                    onCalendarClick = {
                        onCalendarClick()
                    },
                    mealViewModel = mealViewModel,
                )


                CafeteriaViewPager(mealViewModel)
            }
        }
    }
}

@Composable
fun DateSelector(
    onCalendarClick: () -> Unit,
    mealViewModel: MealViewModel,
) {

    val state = mealViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 38.dp,
                bottom = 38.dp,
            )
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 오늘의 급식
            Title1(
                text = stringResource(
                    id = R.string.TodayCafeteria,
                ),
            )


            Space(space = 46.dp)


            // 날짜
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally,
                ),
            ) {

                // left arrow
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp),
                        )
                        .dormClickable {
                            mealViewModel.updateDay(
                                state.selectedDay.minusDays(1),
                            )
                        },
                    painter = painterResource(
                        id = DormIcon.Backward.drawableId,
                    ),
                    contentDescription = null,
                )


                // 날짜 선택
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
                        .dormClickable {
                            onCalendarClick()
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    // 캘린더 아이콘
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_calendar,
                        ),
                        contentDescription = null,
                    )

                    // 날짜
                    Body5(
                        text = "${state.selectedDay}  (${state.selectedDay.dayOfWeek.toKorean()})",
                    )
                }


                // right arrow
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp),
                        )
                        .dormClickable {
                            mealViewModel.updateDay(state.selectedDay.plusDays(1))
                        },
                    painter = painterResource(
                        id = DormIcon.Forward.drawableId,
                    ),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun CafeteriaViewPager(
    mealViewModel: MealViewModel,
) {
    Row(
        modifier = Modifier
            .padding(
                bottom = 100.dp,
            )
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ScrollEffectPager(mealViewModel)
    }
}

private val mealDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

private fun String.toMealDateFormat(): LocalDate = LocalDate.parse(this, mealDateFormatter)

private fun DayOfWeek.toKorean(): String {
    return when (this) {
        MONDAY -> "월"
        TUESDAY -> "화"
        WEDNESDAY -> "수"
        THURSDAY -> "목"
        FRIDAY -> "금"
        SATURDAY -> "토"
        SUNDAY -> "일"
    }
}*/

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
                onIconClicked = onNavigateToNoticeScreen,
            )
            Spacer(Modifier.height(38.dp))
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
            MealCard(
                breakfast = uiState.breakfast,
                kcalOfBreakfast = uiState.kcalOfBreakfast
                    ?: stringResource(R.string.meal_not_exists),
                lunch = uiState.lunch,
                kcalOfLunch = uiState.kcalOfLunch ?: stringResource(R.string.meal_not_exists),
                dinner = uiState.dinner,
                kcalOfDinner = uiState.kcalOfDinner ?: stringResource(R.string.meal_not_exists),
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
        AppLogo()
    }
}

@Composable
private fun NoticeCard(
    onIconClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(18.dp))
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.padding(
                horizontal = 16.dp,
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
            .size(24.dp)
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
            painter = painterResource(
                id = R.drawable.ic_calendar,
            ),
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
private fun ColumnScope.MealCard(
    breakfast: List<String>,
    kcalOfBreakfast: String,
    lunch: List<String>,
    kcalOfLunch: String,
    dinner: List<String>,
    kcalOfDinner: String,
) {
    val pagerState = rememberPagerState(
        initialPage = getProperMeal(),
    )
    HorizontalPager(
        modifier = Modifier.weight(1f),
        pageCount = 3,
        state = pagerState,
        contentPadding = PaddingValues(
            horizontal = 64.dp,
        ),
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                )
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
                    Breakfast -> DishInformation(
                        icon = DormIcon.Breakfast,
                        dishes = breakfast,
                        kcal = kcalOfBreakfast,
                    )

                    Lunch -> DishInformation(
                        icon = DormIcon.Lunch,
                        dishes = lunch,
                        kcal = kcalOfLunch,
                    )

                    Dinner -> DishInformation(
                        icon = DormIcon.Dinner,
                        dishes = dinner,
                        kcal = kcalOfDinner,
                    )
                }
            }
        }
    }
}

@Composable
private fun DishInformation(
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
        items(
            dishes,
        ) { menu ->
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

@Suppress("KotlinConstantConditions")
private fun getProperMeal(): Int {
    val calendar = Calendar.getInstance().apply {
        time = Date()
    }
    return when (calendar.get(Calendar.HOUR)) {
        in BreakfastStartTime until LunchStartTime -> Lunch
        in LunchStartTime until DinnerStartTime -> Dinner
        else -> Breakfast
    }
}
