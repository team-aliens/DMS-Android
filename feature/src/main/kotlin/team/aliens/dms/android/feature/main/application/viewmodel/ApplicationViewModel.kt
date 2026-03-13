package team.aliens.dms.android.feature.main.application.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
    @ApplicationContext private val context: Context,
) : BaseStateViewModel<ApplicationState, Unit>(ApplicationState()) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("application_prefs", Context.MODE_PRIVATE)

    init {
        getAllVotes()
        loadAppliedRemainsTitle()
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

    private fun loadAppliedRemainsTitle() {
        val savedTitle = sharedPreferences.getString(KEY_APPLIED_REMAINS_TITLE, null)
        if (savedTitle != null) {
            setState { it.copy(remainApplicationTitle = savedTitle) }
        }
    }

    internal fun setRemainApplication(title: String) {
        sharedPreferences.edit().putString(KEY_APPLIED_REMAINS_TITLE, title).apply()
        setState { it.copy(remainApplicationTitle = title) }
    }

    companion object {
        private const val KEY_APPLIED_REMAINS_TITLE = "applied_remains_title"
    }
}

data class ApplicationState(
    val votes: List<AllVoteSearch> = emptyList(),
    val remainApplicationTitle: String? = null,
)
