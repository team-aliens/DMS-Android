package team.aliens.dms.android.feature.voting

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.repository.VotingRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
) : BaseMviViewModel<VotingUiState, VotingIntent, VotingSideEffect>(
    initialState = VotingUiState.initial(),
) {
    init {
        fetchAllVoteSearch()
    }

    override fun processIntent(intent: VotingIntent) {
        when (intent) {
            is VotingIntent.UpdateVotingItem -> updateCheckVotingItem(
                intent.votingTopicId,
                intent.voteType,
            )
        }
    }

    private fun fetchAllVoteSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchAllVoteSearch()
            }.onSuccess { fetchedVoteSearch ->
                reduce(
                    newState = stateFlow.value.copy(
                        modelStudentVoteList = fetchedVoteSearch.filter { it.voteType == Vote.MODEL_STUDENT_VOTE },
                        selectedVoteList = fetchedVoteSearch.filter { it.voteType == Vote.OPTION_VOTE },
                        studentVoteList = fetchedVoteSearch.filter { it.voteType == Vote.STUDENT_VOTE },
                        approvalVoteList = fetchedVoteSearch.filter { it.voteType == Vote.APPROVAL_VOTE },
                    ),
                )
            }
        }
    }

    private fun updateCheckVotingItem(votingTopicId: UUID, voteType: Vote) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCheckVotingItem(
                    votingTopicId = votingTopicId,
                )
            }.onSuccess { fetchCheckVotingItem ->
                reduce(
                    newState = stateFlow.value.copy(
                        votingTopicCheckList = listOf(fetchCheckVotingItem),
                    ),
                )
            }
        }
    }
}

data class VotingUiState(
    val selectedVotingOptionId: UUID?,
    val pageCountState: Int,
    val modelStudentVoteList: List<AllVoteSearch>,
    val selectedVoteList: List<AllVoteSearch>,
    val studentVoteList: List<AllVoteSearch>,
    val approvalVoteList: List<AllVoteSearch>,
    val votingTopicCheckList: List<CheckVotingItem>,
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            selectedVotingOptionId = null,
            pageCountState = 0,
            modelStudentVoteList = emptyList(),
            selectedVoteList = emptyList(),
            studentVoteList = emptyList(),
            approvalVoteList = emptyList(),
            votingTopicCheckList = emptyList(),
        )
    }
}

sealed class VotingIntent : Intent() {
    class UpdateVotingItem(val votingTopicId: UUID, val voteType: Vote) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data class MoveToVoteDetail(val voteId: UUID, val voteType: Vote) : VotingSideEffect()
}
