package team.aliens.dms.android.feature.vote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsLayeredButton
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.ui.navigation.LocalResultStore
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.vote.ui.component.VoteItemContent
import team.aliens.dms.android.feature.vote.viewmodel.VoteSideEffect
import team.aliens.dms.android.feature.vote.viewmodel.VoteState
import team.aliens.dms.android.feature.vote.viewmodel.VoteViewModel
import java.util.UUID

@Composable
internal fun Vote(
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel: VoteViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val resultStore = LocalResultStore.current

    LaunchedEffect(Unit) {
        snapshotFlow {
            resultStore.resultStateMap["vote_result"]?.value as? AllVoteSearch?
        }.collect { result ->
            if (result != null) {
                viewModel.initState(result.topicName, result.startTime, result.endTime, result.id)
                resultStore.removeResult<String?>(resultKey = "vote_result")
            } else {
                onShowSnackBar(DmsSnackBarType.ERROR, "정보를 가져오지 못 했어요")
            }
        }


        viewModel.sideEffect.collect {
            when (it) {
                is VoteSideEffect.VoteSuccess -> onShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "투표를 완료했어요!",
                )

                is VoteSideEffect.VoteConflict -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "이미 해당 투표에 참여했어요",
                )

                is VoteSideEffect.VoteFail -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "투표 중 오류가 발생했어요",
                )

                is VoteSideEffect.VoteLoadFail -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "정보를 불러오지 못했어요",
                )
            }
        }
    }

    VoteScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onSelectItem = { selectedId -> viewModel.setSelectId(UUID.fromString(selectedId)) },
        submitVote = viewModel::postVote,
    )
}

@Composable
private fun VoteScreen(
    modifier: Modifier = Modifier,
    state: VoteState,
    onNavigateBack: () -> Unit,
    onSelectItem: (String) -> Unit,
    submitVote: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
    ) {
        DmsTopAppBar(
            onBackPressed = onNavigateBack,
        )
        VoteItemContent(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            vote = state.vote.voteType,
            title = state.vote.topicName,
            startTime = state.vote.startTime,
            endTime = state.vote.endTime,
            options = state.options,
            students = state.students,
            modelStudents = state.modelStudent,
            selectItem = state.selectId.toString(),
            onSelect = onSelectItem,
        )
        DmsLayeredButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "투표하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
            ),
            onClick = submitVote,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}
