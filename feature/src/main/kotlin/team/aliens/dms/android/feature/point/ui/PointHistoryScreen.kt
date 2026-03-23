package team.aliens.dms.android.feature.point.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.point.ui.component.PointItem
import team.aliens.dms.android.feature.point.viewmodel.PointHistoryState
import team.aliens.dms.android.feature.point.viewmodel.PointHistoryViewModel

@Composable
internal fun PointHistory(
    pointType: PointType,
    onBackClick: () -> Unit,
) {
    val viewModel: PointHistoryViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    PointHistoryScreen(
        pointType = pointType,
        state = state,
        onBackClick = onBackClick,
    )
}

@Composable
private fun PointHistoryScreen(
    pointType: PointType,
    state: PointHistoryState,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        DmsTopAppBar(onBackClick = onBackClick, title = "상벌점 내역 확인",)
        val tabData = listOf(
            PointTab.All,
            PointTab.Bonus,
            PointTab.Minus,
        )
        val pagerState = rememberPagerState(
            pageCount = { tabData.size },
            initialPage = pointType.ordinal,
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, tab ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = tab.title,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            beyondViewportPageCount = 1,
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val pointHistoryList = remember(page, state.allPointList) {
                    when (tabData[page]) {
                        PointTab.All -> state.allPointList
                        PointTab.Bonus -> state.bonusPointList
                        PointTab.Minus -> state.minusPointList
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = pointHistoryList,
                        key = { it.id },
                    ) {
                        PointItem(
                            modifier = Modifier.fillMaxWidth(),
                            name = it.name,
                            point = it.score,
                            date = it.date,
                            pointType = it.type,
                        )
                    }
                }
            }
        }
    }
}

internal sealed class PointTab(
    val title: String,
    val pointType: PointType,
) {
    data object All : PointTab(title = "전체", pointType = PointType.ALL)
    data object Bonus : PointTab(title = "상점", pointType = PointType.BONUS)
    data object Minus : PointTab(title = "벌점", pointType = PointType.MINUS)
}
