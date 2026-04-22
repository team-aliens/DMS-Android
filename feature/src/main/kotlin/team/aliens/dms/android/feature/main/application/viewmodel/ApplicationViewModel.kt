package team.aliens.dms.android.feature.main.application.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.latestudy.model.StudyApplicationStatus
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
    private val lateStudyRepository: LateStudyRepository,
    @ApplicationContext private val context: Context,
) : BaseStateViewModel<ApplicationState, Unit>(ApplicationState()) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("application_prefs", Context.MODE_PRIVATE)

    init {
        getAllVotes()
        loadAppliedRemainsTitle()
        fetchMyStudyApplicationStatus()
    }

    private fun getAllVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            votingRepository.fetchAllVoteSearch()
                .onSuccess { votes ->
                    setState { it.copy(votes = votes.toPersistentList()) }
                }.onFailure {
                }
        }
    }

    private fun loadAppliedRemainsTitle() {
        val savedTitle = sharedPreferences.getString(KEY_APPLIED_REMAINS_TITLE, null)
        if (savedTitle != null) {
            setState { it.copy(remainApplicationTitle = savedTitle) }
        }
    }

    private fun fetchMyStudyApplicationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                lateStudyRepository.fetchMyStudyApplicationStatus()
            }.onSuccess { studyApplicationStatus ->
                setState {
                    it.copy(
                        lateStudyApplicationTitle = studyApplicationStatus.toAppliedTitle(),
                    )
                }
            }.onFailure {
                setState {
                    it.copy(lateStudyApplicationTitle = null)
                }
            }
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

@Immutable
data class ApplicationState(
    val votes: ImmutableList<AllVoteSearch> = persistentListOf(),
    val remainApplicationTitle: String? = null,
    val lateStudyApplicationTitle: String? = null,
)

private fun StudyApplicationStatus.toAppliedTitle(): String? {
    return when (status) {
        "FIRST_APPROVED" -> "$startDate ~ $endDate 승인됨"
        "REJECTED" -> "거절됨"
        "PENDING" -> "신청 중"
        else -> null
    }
}
