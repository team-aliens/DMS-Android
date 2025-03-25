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
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.repository.VotingRepository
import java.time.LocalDate
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
                intent.voteOption
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
                        selectedVoteList = fetchedVoteSearch.filter { it.voteType == Vote.OPTION_VOTE },
                        studentVoteList = fetchedVoteSearch.filter { it.voteType == Vote.STUDENT_VOTE },
                        approvalVoteList = fetchedVoteSearch.filter { it.voteType == Vote.APPROVAL_VOTE },
                    ),
                )
            }
        }
    }

    private fun updateCheckVotingItem(voteOption: AllVoteSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCheckVotingItem(
                    votingTopicId = voteOption.id,
                )
            }.onSuccess { fetchCheckVotingItem ->
                reduce(
                    newState = stateFlow.value.copy(
                        votingTopicCheckList = listOf(fetchCheckVotingItem),
                        uiStateTitle = voteOption.topicName,
                    ),
                )
            }
        }
    }

    private fun updateModelList(requestDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchModelStudentCandidates(
                    requestDate = requestDate,
                )
            }.onSuccess { fetchModelList ->
                reduce(
                    newState = stateFlow.value.copy(
                        modelStudentCandidates = listOf(fetchModelList)
                    )
                )
            }
        }
    }

    private fun updateGradeInfo(id: UUID): Boolean = reduce(
        newState = stateFlow.value.copy(
              votingButtonEnabled = stateFlow.value.modelStudentCandidates.find { it.id == id }?.studentGcn!! >= 2000
        ),
    )
}

data class VotingUiState(
    val selectedVotingOptionId: UUID?,
    val pageCountState: Int,
    val uiStateTitle: String,
    val modelStudentCandidates: List<ModelStudentCandidates>,
    val modelStudentVoteList: List<AllVoteSearch>,
    val selectedVoteList: List<AllVoteSearch>,
    val studentVoteList: List<AllVoteSearch>,
    val approvalVoteList: List<AllVoteSearch>,
    val votingTopicCheckList: List<CheckVotingItem>,
    val votingButtonEnabled: Boolean,
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            selectedVotingOptionId = null,
            pageCountState = 0,
            uiStateTitle = "",
            modelStudentCandidates = emptyList(),
            modelStudentVoteList = emptyList(),
            selectedVoteList = emptyList(),
            studentVoteList = emptyList(),
            approvalVoteList = emptyList(),
            votingTopicCheckList = emptyList(),
            votingButtonEnabled = false,
        )
    }
}

sealed class VotingIntent : Intent() {
    class UpdateVotingItem(val voteOption: AllVoteSearch) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data class MoveToVoteDetail(val voteOption: AllVoteSearch) : VotingSideEffect()
}
