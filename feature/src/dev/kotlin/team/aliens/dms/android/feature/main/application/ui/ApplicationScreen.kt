package team.aliens.dms.android.feature.main.application.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.android.core.ui.navigation.LocalResultStore
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.main.application.viewmodel.ApplicationState
import team.aliens.dms.android.feature.main.application.viewmodel.ApplicationViewModel
import team.aliens.dms.kmp.feature.application.ui.component.ApplicationContent
import team.aliens.dms.kmp.feature.application.ui.component.VoteContent

@Composable
internal fun Application(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateVote: (AllVoteSearch) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: ApplicationViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val resultStore = LocalResultStore.current

    LaunchedEffect(Unit) {
        snapshotFlow {
            resultStore.resultStateMap["remain_application_result"]?.value as? String?
        }.collect { result ->
            if (result != null) {
                viewModel.setRemainApplication(result)
                resultStore.removeResult<String?>(resultKey = "remain_application_result")
            }
        }
    }

    ApplicationScreen(
        state = state,
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = { onShowSnackBar(DmsSnackBarType.SUCCESS, "준비중인 기능이에요") },
        onNavigateVolunteerApplication = { onShowSnackBar(DmsSnackBarType.SUCCESS, "준비중인 기능이에요") },
        onNavigateVote = onNavigateVote,
    )
}

@Composable
private fun ApplicationScreen(
    state: ApplicationState,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateVote: (AllVoteSearch) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
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
                        appliedTitle = state.remainApplicationTitle,
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
