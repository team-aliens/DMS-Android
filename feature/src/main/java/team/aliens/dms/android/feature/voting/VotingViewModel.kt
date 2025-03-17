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
import team.aliens.dms.android.data.voting.repository.VotingRepository
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
) : BaseMviViewModel<VotingUiState, VotingIntent, VotingSideEffect>(
    initialState = VotingUiState.initial(),
) {
    private fun allVoteSearch() {
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
}

data class VotingUiState(
    val pageCountState: Int,
    val voteList: List<AllVoteSearch>,
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            pageCountState = 0,
            voteList = emptyList(),
        )
    }
}

sealed class VotingIntent : Intent() {
    data object UpdateVotingItem : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data object Error : VotingSideEffect()
}
