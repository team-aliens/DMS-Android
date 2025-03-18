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
            is VotingIntent.UpdateVotingItem ->
                updateCheckVotingItem(intent.votingTopicId)
        }
    }

    private fun fetchAllVoteSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchAllVoteSearch()
            }.onSuccess { fetchedVoteSearch ->
                reduce(
                    newState = stateFlow.value.copy(
                        voteList = fetchedVoteSearch,
                    ),
                )
            }
        }
    }

    private fun updateCheckVotingItem(votingTopicId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCheckVotingItem(
                    votingTopicId = votingTopicId
                )
            }.onSuccess { fetchCheckVotingItem ->
                reduce(
                    newState = stateFlow.value.copy(
                        votingTopicList = listOf(fetchCheckVotingItem)
                    ),
                )
            }
        }
    }
}

data class VotingUiState(
    val selectedVotingOptionId: UUID?,
    val pageCountState: Int,
    val voteList: List<AllVoteSearch>,
    val votingTopicList: List<CheckVotingItem>
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            selectedVotingOptionId = null,
            pageCountState = 0,
            voteList = emptyList(),
            votingTopicList = emptyList()
        )
    }
}

sealed class VotingIntent : Intent() {
    class UpdateVotingItem(val votingTopicId: UUID) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data object Error : VotingSideEffect()
}
