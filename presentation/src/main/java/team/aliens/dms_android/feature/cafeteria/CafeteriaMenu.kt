package team.aliens.dms_android.feature.cafeteria

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.Body4

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaMenu(
    pages: List<String>,
    pagerState: PagerState,
) {
    // TODO("급식 로직 추후에 개선 필요 급식 넘기기에서 전 화면과 다음 화면 약간 보여준 다음에 넘기는 애니메이션 구현 못함")

    Box() {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
            },
            modifier = Modifier
                .fillMaxHeight(0.72f)
                .fillMaxWidth(0.61f),
            backgroundColor = MaterialTheme.colors.background,
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            color = DormColor.DormPrimary,
                            width = 1.dp,
                            shape = RoundedCornerShape(15)
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TomorrowCafeteria(
    pages: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    pages.forEachIndexed { index, title ->
        Tab(
            text = { Body4(text = title) },
            selected = pagerState.currentPage == index,
            onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            },
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun YesterdayCafeteria(
    pages: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    pages.forEachIndexed { index, title ->
        Tab(text = { Body4(text = title) },
            selected = pagerState.currentPage == index - 1,
            onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(index - 1)
                }
            })
    }
}