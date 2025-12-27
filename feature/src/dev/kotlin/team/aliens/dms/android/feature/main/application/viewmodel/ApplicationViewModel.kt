package team.aliens.dms.android.feature.main.application.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
) : BaseStateViewModel<ApplicationState, Unit>(ApplicationState()) {

    init {
        getAllVotes()
    }

    private fun getAllVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            votingRepository.fetchAllVoteSearch()
                .onSuccess { votes ->
                    setState { it.copy(votes = votes) }
                }.onFailure {
//                    Logger.a(it) { it.message.toString() }
                }
        }
    }

    internal fun setRemainApplication(title: String) {
        setState { it.copy(remainApplicationTitle = title) }
    }
}

data class ApplicationState(
    val votes: List<AllVoteSearch> = emptyList(),
    val remainApplicationTitle: String? = null,
)
