package team.aliens.dms_android.feature.cafeteria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.DormCalendar
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Title1
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.feature.navigator.BottomNavigationItem
import team.aliens.dms_android.feature.navigator.navigateBottomNavigation
import team.aliens.dms_android.viewmodel.home.MealViewModel
import team.aliens.presentation.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun CafeteriaScreen(
    navController: NavHostController,
    bottomTabSelectedItem: MutableState<String>,
    mealViewModel: MealViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()


    val pagerState = rememberPagerState(3)

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

    DormCalendar(
        bottomSheetState = calendarBottomSheetState,
        onChangeDate = onCalendarDateChange,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DormColor.Gray100)
                .paint(painter = painterResource(R.drawable.photo_cafeteria_background),
                    contentScale = ContentScale.FillBounds),
        ) {

            Spacer(modifier = Modifier.height(20.dp))


            CafeteriaTopBar()


            Spacer(modifier = Modifier.height(25.dp))


            ImportantNotice(
                onNoticeIconClick = {
                    navigateBottomNavigation(
                        route = BottomNavigationItem.Notice.route,
                        navController = navController,
                    )
                    bottomTabSelectedItem.value = BottomNavigationItem.Notice.route
                },
            )


            DateSelector(
                onCalendarClick = {
                    onCalendarClick()
                },
                mealViewModel = hiltViewModel(),
            )


            CafeteriaViewPager(mealViewModel)
        }
    }
}

@Composable
private fun CafeteriaTopBar(
) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Image(
            modifier = Modifier
                .height(34.dp)
                .width(97.dp)
                .padding(start = 20.dp, top = 12.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.MainLogo),
        )
    }
}

@Composable
fun ImportantNotice(
    onNoticeIconClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        FloatingNotice(content = stringResource(id = R.string.NewNotice))
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
                vertical = 38.dp,
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


            // 날짜 선택
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                            RoundedCornerShape(4.dp),
                        )
                        .dormClickable {
                            mealViewModel.updateDay(
                                state.selectedDay.minusDays(1),
                            )
                        },
                    painter = painterResource(
                        id = DormIcon.Backward.drawableId,
                    ),
                    contentDescription = stringResource(id = R.string.BackButton),
                )

                // date text
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DormColor.Gray500,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .clip(
                            shape = RoundedCornerShape(5.dp),
                        )
                        .dormClickable {
                            onCalendarClick()
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Body5(text = state.selectedDay.toString())
                }

                // right arrow
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(
                            RoundedCornerShape(4.dp),
                        )
                        .dormClickable {
                            mealViewModel.updateDay(state.selectedDay.plusDays(1))
                        },
                    painter = painterResource(
                        id = R.drawable.ic_meal_next,
                    ),
                    contentDescription = stringResource(id = R.string.NextButton),
                )
            }
        }
    }
}

@Composable
fun CafeteriaViewPager(
    mealViewModel: MealViewModel,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollEffectPager(mealViewModel)
        }
    }
}

private val mealDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

private fun String.toMealDateFormat(): LocalDate = LocalDate.parse(this, mealDateFormatter)
