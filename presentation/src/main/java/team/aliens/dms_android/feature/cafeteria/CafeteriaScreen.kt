package team.aliens.dms_android.feature.cafeteria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.SubTitle1
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.viewmodel.home.MealViewModel
import team.aliens.presentation.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaScreen(
    onNoticeIconClick: () -> Unit,
    mealViewModel: MealViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = mealViewModel) {
        mealViewModel.fetchMeal(mealViewModel.state.value.today)
    }

    val pagerState = rememberPagerState(3)
    val coroutineScope = rememberCoroutineScope()
    val toast = rememberToast()

    LaunchedEffect(Unit) {
        mealViewModel.mealEvent.collect {
            when (it) {
                is MealViewModel.Event.FetchMealSuccess -> {
                    // TODO 급식 정보 불러오기 로직 구현
                }
                else -> {
                    // TODO show toast message
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray100)
            .paint(
                painter = painterResource(R.drawable.photo_cafeteria_background),
                contentScale = ContentScale.FillBounds
            ),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        TopBar()
        Spacer(modifier = Modifier.height(25.dp))
        ImportantNotice(onNoticeIconClick = onNoticeIconClick)
        CafeteriaDiary(pagerState = pagerState, coroutineScope = coroutineScope, hiltViewModel())
        CafeteriaViewPager(mealViewModel)
    }
}

@Composable
fun TopBar(
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
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
        // TODO string resource 로 빼주기
        FloatingNotice(content = stringResource(id = R.string.NewNotice))
        Image(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(33.dp)
                .dormClickable(
                    rippleEnabled = false,
                ){
                    onNoticeIconClick()
                },
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = stringResource(id = R.string.IcNotice),
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaDiary(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    mealViewModel: MealViewModel,
) {
    val state = mealViewModel.state.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.fillMaxHeight(0.055f))
            SubTitle1(text = stringResource(id = R.string.TodayCafeteria))

            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 12.dp)
                        .clickable {
                            mealViewModel.updateDay(state.today.minusDays(1))
                        },
                    painter = painterResource(id = DormIcon.Backward.drawableId),
                    contentDescription = stringResource(id = R.string.BackButton),
                )
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DormColor.Gray500,
                            shape = RoundedCornerShape(25),
                        )
                        .width(130.dp)
                        .height(35.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Body5(text = state.today.toString())
                }
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 12.dp)
                        .clickable {
                            mealViewModel.updateDay(state.today.plusDays(1))
                        },
                    painter = painterResource(id = R.drawable.ic_meal_next),
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
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 23.dp),
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
