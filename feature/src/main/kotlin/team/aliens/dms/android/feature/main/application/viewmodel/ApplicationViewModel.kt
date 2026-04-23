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
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
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
                val title = studyApplicationStatus.toAppliedTitle()
                val statusUi = studyApplicationStatus.toUiStatus()

                setState {
                    it.copy(
                        lateStudyApplicationTitle = title,
                        lateStudyStatusUi = statusUi,
                    )
                }
            }.onFailure {
                setState {
                    it.copy(
                        lateStudyApplicationTitle = null,
                        lateStudyStatusUi = null,
                    )
                }
            }
        }
    }

    internal fun setRemainApplication(title: String) {
        sharedPreferences.edit().putString(KEY_APPLIED_REMAINS_TITLE, title).apply()
        setState { it.copy(remainApplicationTitle = title) }
    }

    internal fun setLateStudyApplication(title: String) {
        setState {
            it.copy(
                lateStudyApplicationTitle = title,
                lateStudyStatusUi = LateStudyStatusUi.PENDING,
            )
        }
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
    val lateStudyStatusUi: LateStudyStatusUi? = null,
)

enum class LateStudyStatusUi {
    APPROVED,
    REJECTED,
    PENDING,
}

private fun StudyApplicationStatusResponse.toAppliedTitle(): String? {
    return when (status) {
        "SECOND_APPROVED" -> {
            if (startDate != null && endDate != null) {
                "$startDate ~ $endDate 승인됨"
            } else {
                null
            }
        }
        "REJECTED" -> null
        "PENDING" -> null
        else -> null
    }
}

private fun StudyApplicationStatusResponse.toUiStatus(): LateStudyStatusUi? {
    return when (status) {
        "SECOND_APPROVED" -> {
            if (startDate != null && endDate != null) {
                LateStudyStatusUi.APPROVED
            } else {
                null
            }
        }
        "REJECTED" -> null
        "PENDING" -> null
        else -> null
    }
}
