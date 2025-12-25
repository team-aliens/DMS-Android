package team.aliens.dms.android.feature.main.application.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.kmp.feature.application.ui.component.ApplicationContent
import team.aliens.dms.kmp.feature.application.ui.component.VoteContent

@Composable
internal fun Application() {
    ApplicationScreen()
}

@Composable
private fun ApplicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val tabData = listOf(
            "신청",
            "투표",
        )
        val pagerState = rememberPagerState(
            pageCount = { tabData.size },
            initialPage = 0,
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, text ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = text,
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
                if (page == 0) {
                    ApplicationContent(
                        onNavigateOutingApplication = onNavigateOutingApplication,
                        onNavigateRemainApplication = onNavigateRemainApplication,
                        onNavigateVolunteerApplication = onNavigateVolunteerApplication,
                    )
                } else {
                    VoteContent(
                        votes = state.votes,
                        onNavigateVote = onNavigateVote,
                    )
                }
            }
        }
    }
}
