package team.aliens.dms_android.feature.cafeteria

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import team.aliens.design_system.component.DormCalendar
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.viewmodel.home.MealViewModel
import team.aliens.dms_android.viewmodel.notice.NoticeViewModel
import team.aliens.presentation.R
import java.time.DayOfWeek
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CafeteriaScreen(
    navController: NavHostController,
    mealViewModel: MealViewModel = hiltViewModel(),
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    onMoveToNotice: () -> Unit,
) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    DormTheme.colors.background,
                )
                .paint(
                    painter = painterResource(R.drawable.photo_cafeteria_background),
                    contentScale = ContentScale.FillBounds
                ),
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            CafeteriaTopBar()

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

@Composable
private fun CafeteriaTopBar(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
    }
}

@Composable
fun ImportantNotice(
    onNoticeIconClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.padding(
            horizontal = 16.dp,
        ),
    ) {
        FloatingNotice(
            content = stringResource(id = R.string.NewNotice),
        )
        Image(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(33.dp)
                .dormClickable(
                    rippleEnabled = false,
                ) {
                    onNoticeIconClick()
                },
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = stringResource(id = R.string.IcNotice),
        )
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


            Spacer(
                modifier = Modifier.height(46.dp),
            )


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
}
